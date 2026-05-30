package com.pekar.nautilusvsmagma.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModConfigSpec
{
    private final List<Entry> entries;

    private ModConfigSpec(List<Entry> entries)
    {
        this.entries = entries;
    }

    public List<Definition<?>> getDefinitions()
    {
        var definitions = new ArrayList<Definition<?>>();
        for (var entry : entries)
        {
            if (entry instanceof DefinitionEntry definitionEntry)
            {
                definitions.add(definitionEntry.definition());
            }
        }

        return List.copyOf(definitions);
    }

    public void load(Path path) throws IOException
    {
        if (Files.exists(path))
        {
            for (var line : Files.readAllLines(path))
            {
                var trimmed = line.trim();
                if (trimmed.isEmpty() || trimmed.startsWith("#") || trimmed.startsWith("["))
                {
                    continue;
                }

                int separator = trimmed.indexOf('=');
                if (separator < 0)
                {
                    continue;
                }

                setValue(trimmed.substring(0, separator).trim(), trimmed.substring(separator + 1).trim());
            }
        }

        save(path);
    }

    private void setValue(String name, String value)
    {
        for (var definition : getDefinitions())
        {
            if (!definition.name.equals(name))
            {
                continue;
            }

            if (definition instanceof BooleanValue booleanValue)
            {
                booleanValue.setValue(Boolean.parseBoolean(value));
            }
            else if (definition instanceof IntValue intValue)
            {
                intValue.setValue(Integer.parseInt(value));
            }

            return;
        }
    }

    public void save(Path path) throws IOException
    {
        if (path.getParent() != null)
        {
            Files.createDirectories(path.getParent());
        }

        try (var writer = Files.newBufferedWriter(path))
        {
            for (var entry : entries)
            {
                if (entry instanceof SectionEntry section)
                {
                    writer.write("[" + section.name() + "]");
                    writer.newLine();
                    continue;
                }

                var definition = ((DefinitionEntry) entry).definition();
                for (var comment : definition.getComment())
                {
                    writer.write("\t#" + comment);
                    writer.newLine();
                }

                writer.write("\t" + definition.name + " = " + definition.getValue());
                writer.newLine();
            }
        }
    }

    public static class BooleanValue extends Definition<Boolean>
    {
        private BooleanValue(String name, boolean defaultValue)
        {
            super(name, defaultValue);
        }

        public static BooleanValue define(String name, boolean defaultValue)
        {
            return new BooleanValue(name, defaultValue);
        }

        public boolean isTrue()
        {
            return getValue();
        }

        public boolean isFalse()
        {
            return !getValue();
        }

        public boolean get()
        {
            return getValue();
        }
    }

    public static class IntValue extends Definition<Integer>
    {
        private final int min;
        private final int max;

        private IntValue(String name, int defaultValue, int min, int max)
        {
            super(name, defaultValue);
            this.min = min;
            this.max = max;
        }

        public static IntValue define(String name, int defaultValue, int min, int max)
        {
            return new IntValue(name, defaultValue, min, max);
        }

        public int getAsInt()
        {
            return getValue();
        }

        @Override
        public void setValue(Integer value)
        {
            var val = Math.clamp(value, min, max);
            super.setValue(val);
        }
    }

    public static class Builder
    {
        private final List<String> comments = new ArrayList<>();
        private final List<Entry> entries = new ArrayList<>();

        public Builder comment(String... textLines)
        {
            comments.addAll(Arrays.asList(textLines));
            return this;
        }

        public Builder push(String name)
        {
            entries.add(new SectionEntry(name));
            return this;
        }

        public Builder pop()
        {
            return this;
        }

        public BooleanValue define(String name, boolean defaultValue)
        {
            var definition = BooleanValue.define(name, defaultValue);
            for (var comment : comments)
            {
                definition.addComment(comment);
            }

            comments.clear();
            entries.add(new DefinitionEntry(definition));

            return definition;
        }

        public IntValue defineInRange(String name, int defaultValue, int min, int max)
        {
            var definition = IntValue.define(name, defaultValue, min, max);
            for (var comment : comments)
            {
                definition.addComment(comment);
            }

            comments.clear();
            entries.add(new DefinitionEntry(definition));

            return definition;
        }

        public ModConfigSpec build()
        {
            return new ModConfigSpec(List.copyOf(entries));
        }
    }
}
