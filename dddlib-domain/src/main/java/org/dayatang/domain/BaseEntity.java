/**
 *
 */
package org.dayatang.domain;

import javax.persistence.*;

/**
 * 抽象实体类，可作为所有领域实体的基类。
 *
 * @author yang
 *
 */
@MappedSuperclass
public abstract class BaseEntity implements Entity {

    private static final long serialVersionUID = 8882145540383345037L;

    /**
     * 判断该实体是否已经存在于数据库中。
     * @return 如果数据库中已经存在拥有该id的实体则返回true，否则返回false。
     */
    @Override
    public boolean existed() {
        Object id = getId();
                
        if (id == null) {
            return false;
        }
        if (id instanceof Number && ((Number)id).intValue() == 0) {
            return false;
        }
        return getRepository().exists(getClass(), getId());
    }

    /**
     * 判断该实体是否不存在于数据库中。
     * @return 如果数据库中已经存在拥有该id的实体则返回false，否则返回true。
     */
    @Override
    public boolean notExisted() {
        return !existed();
    }

    private static EntityRepository repository;

    /**
     * 获取仓储对象实例。如果尚未拥有仓储实例则通过InstanceFactory向IoC容器获取一个。
     * @return 仓储对象实例
     */
    public static EntityRepository getRepository() {
        if (repository == null) {
            repository = InstanceFactory.getInstance(EntityRepository.class);
        }
        return repository;
    }

    /**
     * 设置仓储实例。该方法主要用于单元测试。产品系统中通常是通过IoC容器获取仓储实例。
     * @param repository 要设置的仓储对象实例
     */
    public static void setRepository(EntityRepository repository) {
        BaseEntity.repository = repository;
    }

    /**
     * 获取哈希值。每个实体类都必须覆盖该方法，它与equals()方法成对存在，用于判定两个实体是否等价。
     * 等价的两个实体的hashCode相同，不等价的两个实体hashCode不同。
     * @return 实体的哈希值
     */
    @Override
    public abstract int hashCode();

    /**
     * 判断两个实体是否等价。每个实体类都必须覆盖该方法，hashCode()方法成对存在。
     * 等价的两个实体的hashCode相同，不等价的两个实体hashCode不同。
     * @param other 另一个实体
     * @return 如果本实体和other等价则返回true,否则返回false
     */
    @Override
    public abstract boolean equals(Object other);
}