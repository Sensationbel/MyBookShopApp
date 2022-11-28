-- changeSet Sensationbel:1-5
CREATE TYPE type_contact AS ENUM ('EMAIL','PHONE');
-- ALTER TABLE user_contact ALTER COLUMN `type` type_contact;