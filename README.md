![Essence](https://cdn.modrinth.com/data/cached_images/1a9959eb6d98d5e50f561c021de086a8212cc7cc.png)

- 💾 **Download Essence** - https://lewmc.net/plugin/essence
- 🔧 **View the JavaDocs** - https://lewmc.github.io/Essence
- 🌐 **Help Translate Essence** - https://crowdin.com/project/lewmc-essence
- ⭐ Enjoying Essence? We'd love to hear your feedback on Spigot. Leave us a review [here](https://www.spigotmc.org/resources/essence.114553/).

[![Crowdin](https://badges.crowdin.net/lewmc-essence/localized.svg)](https://crowdin.com/project/lewmc-essence) [![Maven Build](https://github.com/LewMC/Essence/actions/workflows/maven.yml/badge.svg)](https://github.com/LewMC/Essence/actions/workflows/maven.yml)

## Build Process

Install JDK 21 before continuing. Click [here](https://docs.oracle.com/en/java/javase/21/install/index.html) for documentation.

- You will also need Maven for the `mvn` command, which can be installed [here](https://maven.apache.org/download.cgi).
- Make sure that your version of JDK 21 includes JavaDoc.
  - For example, Eclipse Temurin JDK with Hotspot 21 includes this executable.

```sh
# Clone the repository and move into it.
git clone https://github.com/lewmc/essence && cd essence

# Perform a clean build (optional if you're rebuilding).
mvn clean package -Dmaven.test.skip=true

# Build the package with an explicit version target of 21.
mvn -B package --file pom.xml -Dmaven.compiler.source=21 -Dmaven.compiler.target=21
```

## Contributing

We welcome contributions from the community. Please fork the repository, make your changes, and submit a pull request.

When adding new features for the next update, please merge them into the `next-update` branch. This helps us keep our CI Builds separate as `-SNAPSHOT` builds.

## Licensing

Essence is licensed under the Apache License 2.0. See [LICENSE](LICENSE) for more information.
