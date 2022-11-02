ALTER TABLE public.pedido
    ADD COLUMN codigo character varying(36);
	
update pedido set codigo = gen_random_uuid();

ALTER TABLE public.pedido
ALTER COLUMN codigo SET NOT NULL;

ALTER TABLE public.pedido
ADD CONSTRAINT codigo_uuid UNIQUE (codigo);