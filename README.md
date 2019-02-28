# L3PL

L3 - Projet de licence

## Table of contents

- [Iterations](./Documentation/iterations.md)
- [Set up guide](./Documentation/setup.md)
- [GUI documentation](./Documentation/IHM/DocumentationIHM.md)

## Group

- William Poitevin: Ainorte
- Ivan Delsinne: IvanDelsinne
- Florian Regin: Flo-Info
- Derek Samson: dex06
- Maxime Soumrany: Rohydre

## Exchange protocol

The exchange protocol is based on keywords (a close variant of the RPC protocol).

The class to use when (de)serializing messages is `unice.plfgd.common.net.Exchange`, and the key to use when exchanging through the Exchange format is `message`.

The table below details every defined keyword.

|Keyword|Parameters|Description|
|-------|----------|-----------|
|ident|An Identifier object|Authenticates the remote user|
|question|An Answer object|The user's trying to guess the number|
