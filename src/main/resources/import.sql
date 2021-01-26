INSERT INTO genres(genre_id, genre_name) VALUES (nextval('genre_sequence'), 'Sci-Fi');
INSERT INTO genres(genre_id, genre_name) VALUES (nextval('genre_sequence'), 'Crime');
INSERT INTO genres(genre_id, genre_name) VALUES (nextval('genre_sequence'), 'Thriller');

INSERT INTO languages(language_id, language_name) VALUES (nextval('language_sequence'), 'English');
INSERT INTO languages(language_id, language_name) VALUES (nextval('language_sequence'), 'Italian');
INSERT INTO languages(language_id, language_name) VALUES (nextval('language_sequence'), 'Spanish');

INSERT INTO performer(performer_id, fullname) VALUES (nextval('performer_sequence'), 'Daniel Day-Lewis');
INSERT INTO performer(performer_id, fullname) VALUES (nextval('performer_sequence'), 'Marion Cotillard');

INSERT INTO movies(movie_id, description, media_path, release_year, movie_title) VALUES(nextval('movie_sequence'), 'After discovering a mysterious artifact buried beneath the Lunar surface, mankind sets off on a quest to find its origins with help from intelligent supercomputer H.A.L. 9000.', 'URL', '1968', '2001: A Space Odyssey');

INSERT INTO movies(movie_id, description, media_path, release_year, movie_title) VALUES(nextval('movie_sequence'), 'A story of family, religion, hatred, oil and madness, focusing on a turn-of-the-century prospector in the early days of the business.', 'URL', '2007', 'There Will Be Blood');

INSERT INTO movie_genre(movie_id, genre_id) VALUES (1, 1);

INSERT INTO movie_language(movie_id, language_id) VALUES (1, 1);
INSERT INTO movie_language(movie_id, language_id) VALUES (1, 2);
INSERT INTO movie_language(movie_id, language_id) VALUES (2, 1);

INSERT INTO movie_performer(movie_id, performer_id, performer_role) VALUES (2, 1, 'Daniel Plainview');