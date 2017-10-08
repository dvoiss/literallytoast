package com.dvoiss.literallytoast;

import android.content.Context;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertNotNull;

public class CreateToastTest {

    @Mock Context context;

    @Test
    public void createsToast() throws Exception {
        assertNotNull(Toast.create(context, "Test", Toast.LENGTH_SHORT));
    }
}
