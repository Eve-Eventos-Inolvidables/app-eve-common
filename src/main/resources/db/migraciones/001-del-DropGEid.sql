--liquibase formatted sql

--changeset mrfabian:001-del-DropGEid
ALTER TABLE events
DROP COLUMN group_event_id;

