--liquibase formatted sql
--changeset mrfabian:005-add-col-isarchived-3tab.sql

ALTER TABLE buys
ADD is_archived bool;

ALTER TABLE tickets
ADD is_archived bool;

ALTER TABLE user_interests
ADD is_archived bool;


