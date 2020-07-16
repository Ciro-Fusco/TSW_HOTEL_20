package it.unisa.control;

import it.unisa.model.Cart;
import it.unisa.model.camera.CameraBean;
import it.unisa.model.camera.CameraDAO;
import it.unisa.model.cliente.ClienteBean;
import it.unisa.model.cliente.ClienteDAO;
import it.unisa.model.prenotazione.PrenotazioneBean;
import it.unisa.model.prenotazione.PrenotazioneDAO;
import it.unisa.model.user.UserBean;
import it.unisa.model.user.UserDAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet("/doReservationControl")
public class makeReservation extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        HttpSession session = request.getSession();
        UserBean logged = (UserBean) session.getAttribute("user");
        ClienteBean customerLogged = (ClienteBean) session.getAttribute("customer");
        Cart cart = (Cart) session.getAttribute("cart");

        String action = request.getParameter("action");
        session.removeAttribute("action");
        switch (action) {
            case "create":
                boolean toRegister = false;
                String email = "";
                String password = "";
                String tipo = request.getParameter("tipocamera");
                String date = request.getParameter("dates");
                String check_in = bookeDateMaker(date.substring(0, 10), true);
                String check_out = bookeDateMaker(date.substring(13, 23), true);

                if (request.getParameter("register") != null) {
                    if(logged == null) {
                        toRegister = true;
                        email = request.getParameter("email");
                        password = request.getParameter("password");
                    }
                }

                try {
                    if (!PrenotazioneDAO.validate(check_in, check_out, tipo))
                        response.sendRedirect(response.encodeRedirectURL("./prenotazione/prenotazione.jsp?error=already-booked"));

                    else{
                        request.getSession().removeAttribute("cart");
                        cart = new Cart();

                        if(customerLogged == null) {
                            ClienteBean clienteBean = new ClienteBean();
                            clienteBean.setCodicefiscale(request.getParameter("codicefiscale"));
                            clienteBean.setNome(request.getParameter("nome"));
                            clienteBean.setCognome(request.getParameter("cognome"));
                            clienteBean.setDatanascita(bookeDateMaker(request.getParameter("nascita"), false));
                            cart.setClienteBean(clienteBean);
                        }
                        else {
                            cart.setClienteBean(customerLogged);
                        }

                        if (!ClienteDAO.isCustomer(cart.getClienteBean().getCodicefiscale())) cart.setAddClient(true);
                        else cart.setAddClient(false);

                        if (toRegister) {
                            UserBean userBean = new UserBean();
                            userBean.setEmail(email);
                            userBean.setPassword(password);
                            userBean.setCodicefiscale(cart.getClienteBean().getCodicefiscale());
                            userBean.setAdmin(false);

                            cart.setUserBean(userBean);
                            if (!UserDAO.isAlreadyUserEmail(email)) cart.setAddUser(true);
                        }
                        else cart.setAddUser(false);

                        int numberRoom = PrenotazioneDAO.getFirstFreeByType(check_in, check_out, tipo);
                        CameraBean cameraBean = CameraDAO.getRoomByNumber(numberRoom);
                        cart.setCameraBean(cameraBean);

                        PrenotazioneBean toAdd = new PrenotazioneBean(); //Crea il bean//Recupera il primo numero di camera libero
                        toAdd.setCodice_fiscale(cart.getClienteBean().getCodicefiscale());
                        toAdd.setCheck_in(new Timestamp((new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(check_in)).getTime()));
                        toAdd.setCheck_out(new Timestamp((new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(check_out)).getTime()));
                        toAdd.setNumero(numberRoom);

                        cart.setPrenotazioneBean(toAdd);
                        session.setAttribute("cart", cart);

                        if(UserDAO.isAlreadyUserEmail(email) && toRegister){
                            session.removeAttribute("cart");
                            cart.getClienteBean().setDatanascita(dateBookedMaker(cart.getClienteBean().getDatanascita()));
                            session.setAttribute("cart", cart);
                            response.sendRedirect(response.encodeRedirectURL("./prenotazione/prenotazione.jsp?error=user-already-exist&action=modify"));
                        }

                        else response.sendRedirect(response.encodeRedirectURL("./prenotazione/riepilogo.jsp"));
                        }
                } catch (SQLException | ParseException throwables) {
                    response.sendRedirect(response.encodeRedirectURL("./prenotazione/prenotazione.jsp?error=errore-generico"));
                    throwables.printStackTrace();
                }
                break;
            case "booked":
                try {
                    if (cart.isAddClient()) ClienteDAO.addCostumer(cart.getClienteBean());
                    if (cart.isAddUser()) UserDAO.addStandardUser(cart.getUserBean());

                    PrenotazioneDAO.addReservation(cart.getPrenotazioneBean());

                    session.removeAttribute("cart");

                    response.sendRedirect(response.encodeRedirectURL("./index.jsp?action=booked"));

                } catch (SQLException sqlException) {
                    response.sendRedirect(response.encodeRedirectURL("./prenotazione/prenotazione.jsp"));
                    sqlException.printStackTrace();
                }
                break;
            case "delete":
                request.getSession().removeAttribute("cart");
                response.sendRedirect(response.encodeRedirectURL("./index.jsp?action=deleted"));
                break;
            case "modify":
                cart.getClienteBean().setDatanascita(dateBookedMaker(cart.getClienteBean().getDatanascita()));
                response.sendRedirect(response.encodeRedirectURL("./prenotazione/prenotazione.jsp?action=modify"));

        }

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
         doPost(request,response);
    }

    protected String bookeDateMaker(String date, boolean booked){ // Booleano usato per riutilizzare la stessa funzione nel caso si tratti di una data di prenotazione o di nascita
        String month = date.substring(0,2);
        String day = date.substring(3,5);
        String year = date.substring(6,10);
        if (booked) return year + "-" + month + "-" + day + " 10:00:00";
        else return year + "-" + month + "-" + day;
    }
    protected String dateBookedMaker(String date){
        String year = date.substring(0,4);
        String month = date.substring(5,7);
        String day = date.substring(8,10);

        return month + "/" + day + "/" + year;
    }
}
