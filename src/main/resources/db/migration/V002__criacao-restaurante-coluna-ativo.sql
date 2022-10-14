alter table restaurante add column ativo int;
update restaurante set ativo=1;
alter table restaurante alter column ativo set not null;