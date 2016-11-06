package com.ssa.ironyard.fitness.crypto;

import com.ssa.ironyard.fitness.model.Password;

public interface SecurePassword {

    /**
     *
     * @param clearText
     *            a raw password, un-hashed, un-encrypted
     * @return a Password suitable for secure persistent storage
     */
    Password secureHash(String clearText);

    /**
     * Does the <code>clearText</code> match the secure password?
     * 
     * @param clearText
     * @param secure
     * @return
     */
    boolean verify(String clearText, Password secure);
}
