CREATE TABLE users
(
  id         serial PRIMARY KEY,
  name       VARCHAR(50) UNIQUE NOT NULL,
  password   VARCHAR(255)       NOT NULL,
  created_at TIMESTAMP          NOT NULL,
  updated_at TIMESTAMP
);

CREATE TABLE user_games
(
  id         SERIAL PRIMARY KEY,
  user_id    INT       NOT NULL,
  finished   BOOLEAN   NOT NULL DEFAULT FALSE,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE rps_games
(
  id           SERIAL PRIMARY KEY,
  user_game_id INT         NOT NULL,
  user_choice  VARCHAR(50) NOT NULL,
  ai_choice    VARCHAR(50) NOT NULL,
  result       VARCHAR(50) NOT NULL,
  created_at   TIMESTAMP   NOT NULL,

  FOREIGN KEY (user_game_id) REFERENCES user_games (id) ON DELETE CASCADE
)