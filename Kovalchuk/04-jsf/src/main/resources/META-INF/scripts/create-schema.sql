-- Sorry, I do not know the way to make JPA recognize multi-line SQL
CREATE TABLE IF NOT EXISTS sinner (id SERIAL PRIMARY KEY, first_name TEXT NOT NULL, last_name TEXT NOT NULL, birth_date DATE NOT NULL DEFAULT current_date, death_date DATE);
CREATE TABLE IF NOT EXISTS execution (id SERIAL PRIMARY KEY, sinner_id INTEGER NOT NULL, description TEXT NOT NULL, place TEXT, date TIMESTAMP WITH TIME ZONE);
CREATE TABLE IF NOT EXISTS accuse (id SERIAL PRIMARY KEY, sinner_id INTEGER NOT NULL, accuses TEXT NOT NULL);

ALTER TABLE execution DROP CONSTRAINT IF EXISTS execution_sinner_id_fkey;
ALTER TABLE execution ADD CONSTRAINT execution_sinner_id_fkey FOREIGN KEY (sinner_id) REFERENCES sinner (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE accuse DROP CONSTRAINT IF EXISTS accuse_sinner_id_fkey;
ALTER TABLE accuse ADD CONSTRAINT accuse_sinner_id_fkey FOREIGN KEY (sinner_id) REFERENCES sinner (id) ON DELETE CASCADE ON UPDATE CASCADE;
