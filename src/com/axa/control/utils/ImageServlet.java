
package com.axa.control.utils;

import java.io.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;

@WebServlet({"/upload"})
@MultipartConfig
public class ImageServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(ImageServlet.class);

    public ImageServlet() {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("Recibiendo imagen");
        String type = request.getHeader("content-type");
        String nombreLote = request.getParameter("lote");
        log.debug("Nombre del lote:"+nombreLote);

        log.debug("Multipart: " + request.getContentType());
        if (request.getContentType().contains("multipart/form-data")) {
            Collection parts = request.getParts();
            log.debug("Tamaño: " + parts.size());
            Iterator it = parts.iterator();


            while (true) {
                Part item;
                FileOutputStream outputStream = null;
                InputStream is = null;
                String name = null;
                do {

                    do {
                        if (!it.hasNext()) {
                            log.debug("tipo" + type);
                            return;
                        }

                        item = (Part) it.next();
                        log.debug("Nombre:" + item.getName());
                        log.debug("Contenido: " + item.getContentType());
                        log.debug("Tamaño:" + item.getSize());
                        Random random = new Random();
                        Integer rand = random.nextInt();
                        if (item.getContentType() != null) {
                            name = nombreLote + rand + ".jpg";
                            outputStream = new FileOutputStream(new File("/Users/seaXom/Downloads/" + name));
                            is = item.getInputStream();
                        }
                    } while (item.getContentType() == null);
                } while (!item.getContentType().contentEquals("image/jpeg"));


                try {
                    boolean e = false;
                    byte[] bytes = new byte[1024];

                    int e1;
                    while ((e1 = is.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, e1);
                    }


                    PrintWriter out = response.getWriter();
                    out.println("<br>Imagen cargada: " + name);


                } catch (IOException var23) {
                    var23.printStackTrace();
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException var22) {
                            var22.printStackTrace();
                        }
                    }

                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException var21) {
                            var21.printStackTrace();
                        }
                    }

                }
            }
        }
    }
}
