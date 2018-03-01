INSERT INTO
  departments(name, address)
  VALUES ('White House', 'Address of Dept_1');
INSERT INTO
  departments(name, address)
  VALUES ('Guards', 'Russia, Nsk, pr. Marksa');
INSERT INTO
  departments(name, address)
  VALUES ('NASA', 'USA, Washington D.C.');

INSERT INTO
  workers(name, last_name, position, department_id, hiring_date, resignation_date, birthday)
  VALUES('Denis', 'Medvedev', 'Developer', 3, '2016-12-09T09:00:00Z', '2018-03-12T09:00:00Z', '1992-03-18T01:30:00Z');
INSERT INTO
  workers(name, last_name, position, department_id, hiring_date, resignation_date, birthday)
  VALUES('John', 'Diggle', 'Guard', 2, '2010-01-09T09:00:00Z', NULL, '1980-10-11T12:30:00Z');
INSERT INTO
  workers(name, last_name, position, department_id, hiring_date, resignation_date, birthday)
  VALUES('Oliver', 'Queen', 'Green Arrow', 3, '2009-11-29T09:00:00Z', NULL, '1981-07-24T19:55:00Z');

INSERT INTO
  worked_hours(worker_id, start_date, finish_date)
  VALUES (1, '2018-02-17T09:00:00Z', '2018-02-17T23:00:00Z');
INSERT INTO
  worked_hours(worker_id, start_date, finish_date)
  VALUES (1, '2018-02-16T09:00:00Z', '2018-02-16T19:00:00Z');
INSERT INTO
  worked_hours(worker_id, start_date, finish_date)
  VALUES (1, '2018-02-15T10:00:00Z', '2018-02-17T17:00:00Z');