package com.pekar.nautilusvsmagma.config;

interface Entry
{
}

record SectionEntry(String name) implements Entry
{
}

record DefinitionEntry(Definition<?> definition) implements Entry
{
}
