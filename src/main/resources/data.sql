-- ---------------------------------
-- 1. INSERT LEAGUES
-- ---------------------------------
-- The 'country' column MUST match the exact Java enum constant name.
-- E.g., 'UNITED_KINGDOM' instead of 'ENGLAND'.

INSERT INTO league (id, name, country) VALUES (1, 'Premier League', 'UNITED_KINGDOM');
INSERT INTO league (id, name, country) VALUES (2, 'La Liga', 'SPAIN');
INSERT INTO league (id, name, country) VALUES (3, 'Bundesliga', 'GERMANY');
INSERT INTO league (id, name, country) VALUES (4, 'Ligue 1', 'FRANCE'); -- Added another example


-- ---------------------------------
-- 2. INSERT CLUBS
-- ---------------------------------
-- No changes needed here, as the relationships are based on league_id.

-- Premier League Clubs (league_id = 1)
INSERT INTO club (id, name, founded_year, league_id) VALUES (10, 'Manchester United', 1878, 1);
INSERT INTO club (id, name, founded_year, league_id) VALUES (11, 'Liverpool', 1892, 1);
INSERT INTO club (id, name, founded_year, league_id) VALUES (12, 'Arsenal', 1886, 1);

-- La Liga Clubs (league_id = 2)
INSERT INTO club (id, name, founded_year, league_id) VALUES (20, 'Real Madrid', 1902, 2);
INSERT INTO club (id, name, founded_year, league_id) VALUES (21, 'Barcelona', 1899, 2);

-- Bundesliga Clubs (league_id = 3)
INSERT INTO club (id, name, founded_year, league_id) VALUES (30, 'Bayern Munich', 1900, 3);

-- Ligue 1 Club (league_id = 4)
INSERT INTO club (id, name, founded_year, league_id) VALUES (40, 'Paris Saint-Germain', 1970, 4);