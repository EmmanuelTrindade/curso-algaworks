alter table restaurante add column aberto int;
update restaurante set aberto=0;
alter table restaurante alter column aberto set not null;