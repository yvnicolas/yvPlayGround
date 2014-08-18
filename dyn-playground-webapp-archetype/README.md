dyn-webapp-archetype
====================

A maven archetype to generate a web application inside the dynamease environment.
Once you have downloaded the archetype directory do an `mvn install` inside this directory.

To generate a project, do with appropriate modifications:

```
mvn  archetype:generate \
 -DarchetypeGroupId=com.dynamease \
 -DarchetypeArtifactId=dyn-webapp-archetype \
 -DarchetypeVersion=1.0-SNAPSHOT \
 -DgroupId=com.dynamease \
 -DartifactId=test-arche-dyn
```

You can also execute the `genDynWebProj.bsh` shell script which contains this above command.