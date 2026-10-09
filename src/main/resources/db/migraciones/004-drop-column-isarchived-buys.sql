--liquibase formatted sql
--changeset mrfabian:004-drop-column-isarchived-buys.sql

ALTER TABLE buys
DROP COLUMN is_archived;
