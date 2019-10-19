CREATE TABLE following (
  id                  SERIAL       NOT NULL PRIMARY KEY,
  follower_user_id    INT          NOT NULL,
  followed_user_id    INT          NOT NULL,
  created_date        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,

  FOREIGN KEY (follower_user_id) REFERENCES user (id) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (followed_user_id) REFERENCES user (id) ON DELETE CASCADE ON UPDATE CASCADE
);