DELETE FROM users
WHERE username <> 'admin';

UPDATE users
SET password = '$2a$10$KbE.e8dn3R9KcEfg/QTglu.yksJEDhnzI2qjYUEKFdOgve4AyBlZq'
WHERE username = 'admin';