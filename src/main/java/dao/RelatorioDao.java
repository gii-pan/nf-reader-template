package dao;

import model.Relatorio;

import javax.persistence.EntityManager;

public class RelatorioDao {

    private EntityManager entity;

    public RelatorioDao(EntityManager entity) {
        this.entity = entity;
    }

    public void salvarRelatorio(Relatorio relatorio) {
        this.entity.persist(relatorio);
    }
}
