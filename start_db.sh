#!/bin/sh

docker run --restart always \
           --publish=7474:7474 \
           --publish=7687:7687 \
           --env NEO4J_AUTH=none \
           -e NEO4J_apoc_export_file_enabled=true \
           -e NEO4J_apoc_import_file_enabled=true \
           -e NEO4J_apoc_import_file_use__neo4j__config=true \
           -e NEO4J_PLUGINS=\[\"apoc\"\] \
           --volume=$HOME/neo4j/data:/data \
           --volume=$HOME/neo4j/import:/import \
           neo4j:5.14.0