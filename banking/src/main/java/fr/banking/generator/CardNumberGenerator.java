package fr.banking.generator;

import org.hibernate.HibernateException;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

public class CardNumberGenerator implements IdentifierGenerator {
    public static final String NAME = "cardNumberGenerator";
    //Permet d'avoir un numéro de carte unique à 16 chiffres
    public static final AtomicLong atomicLong = new AtomicLong(1000000000000000L);
    @Override
    public Serializable generate(org.hibernate.engine.spi.SharedSessionContractImplementor session, Object object) throws HibernateException {
        return String.valueOf(atomicLong.getAndIncrement());
    }
}