ALTER TABLE execution DROP CONSTRAINT IF EXISTS execution_sinner_id_fkey;
ALTER TABLE accuse DROP CONSTRAINT IF EXISTS accuse_sinner_id_fkey;

DROP TABLE sinner CASCADE;
DROP TABLE execution CASCADE;
DROP TABLE accuse CASCADE;
