-- Kustutab bank schema (mis põhimõtteliselt kustutab kõik tabelid)
DROP SCHEMA IF EXISTS bank CASCADE;
-- Loob uue public schema vajalikud õigused
CREATE SCHEMA bank
-- taastab vajalikud andmebaasi õigused
    GRANT ALL ON SCHEMA bank TO postgres;
GRANT ALL ON SCHEMA bank TO PUBLIC;

-- Määrab, et kõik selle andmebaasiga tehtavad uued ühendused kasutavad
-- vaikimisi bank skeemat, mitte public't. Ilma selleta looks järgmine
-- skript (2_create.sql) tabelid public skeemas, kui seda käivitatakse
-- eraldi ühendusena.
ALTER DATABASE vali_it SET search_path TO bank, public;