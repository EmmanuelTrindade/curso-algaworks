CREATE TABLE IF NOT EXISTS public.restaurante_usuario_responsavel
(
    restaurante_id bigint NOT NULL,
    usuario_id bigint NOT NULL,
    PRIMARY KEY (restaurante_id, usuario_id),
    CONSTRAINT fk_restaurante_usuario_restaurante FOREIGN KEY (restaurante_id)
        REFERENCES public.restaurante (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT fk_restaurante_usuario_usuario FOREIGN KEY (usuario_id)
        REFERENCES public.usuario (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE public.restaurante_usuario_responsavel
    OWNER to md2net;