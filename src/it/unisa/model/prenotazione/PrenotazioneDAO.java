package it.unisa.model.prenotazione;

import it.unisa.model.DriverManagerConnectionPool;
import it.unisa.model.cliente.ClienteDAO;
import it.unisa.model.user.UserDAO;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class PrenotazioneDAO {

    // Ritorna tutte le prenotazioni per un determinato codice fiscale
    public static Collection<PrenotazioneBean> doRetriveByCF(String codice_fiscale){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        ArrayList<PrenotazioneBean> prenotazioneBeans = new ArrayList<PrenotazioneBean>();
        String statement = "SELECT Prenotazione.* FROM Prenotazione, Cliente WHERE Cliente.codice_fiscale = ? and Cliente.codice_fiscale = Prenotazione.codice_fiscale;";
        try{
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1, codice_fiscale);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                PrenotazioneBean bean = new PrenotazioneBean();
                bean.setCodice_fiscale(resultSet.getString("codice_fiscale"));
                bean.setNumero(resultSet.getInt("numero"));
                bean.setCheck_in(resultSet.getTimestamp("check_in"));
                bean.setCheck_out(resultSet.getTimestamp("check_out"));
                prenotazioneBeans.add(bean);
            }

            return prenotazioneBeans;

        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        } finally {
            try{
                if(preparedStatement != null) preparedStatement.close();
                DriverManagerConnectionPool.releaseConnection(connection);
            }
            catch(SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return null;
    }

    public static Collection<PrenotazioneBean> allBooking(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        ArrayList<PrenotazioneBean> prenotazioneBeans = new ArrayList<PrenotazioneBean>();
        String statement = "SELECT * FROM Prenotazione";
        try{
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(statement);

            ResultSet res = preparedStatement.executeQuery();

            while (res.next()){
                PrenotazioneBean bean = new PrenotazioneBean();
                bean.setCodice_fiscale(res.getString("codice_fiscale"));
                bean.setNumero(res.getInt("numero"));
                bean.setCheck_in(res.getTimestamp("check_in"));
                bean.setCheck_out(res.getTimestamp("check_out"));
                prenotazioneBeans.add(bean);
            }
            return prenotazioneBeans;
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        } finally {
            try{
                if(preparedStatement != null) preparedStatement.close();
                DriverManagerConnectionPool.releaseConnection(connection);
            }
            catch(SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return null;
    }
    //Rimuove una prenotazione esistente
    public static boolean removeReservation(String codice_fiscale, String check_in, String check_out, int numero){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String statement = "DELETE from Prenotazione where Prenotazione.codice_fiscale = ? and Prenotazione.check_in = ? and Prenotazione.check_out = ? and Prenotazione.numero = ?";
        try{
            connection = DriverManagerConnectionPool.getConnection();

            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1,codice_fiscale);
            preparedStatement.setString(2,check_in);
            preparedStatement.setString(3,check_out);
            preparedStatement.setInt(4,numero);

            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();
            return true;
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        finally {
            try{
                if(preparedStatement != null) preparedStatement.close();
                DriverManagerConnectionPool.releaseConnection(connection);
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
        return false;
    }

    //TO ADD A RESERVATION

    //Aggiunge una prenotazione sia con dati utente che con dati cliente
    public static void addReservation(PrenotazioneBean toAdd) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String statement = "INSERT INTO Prenotazione(codice_fiscale, numero, check_in, check_out) VALUES (?,?,?,?);";

        try{
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1,toAdd.getCodice_fiscale());
            preparedStatement.setInt(2, toAdd.getNumero());
            preparedStatement.setString(3,toAdd.getCheck_in().toString());
            preparedStatement.setString(4,toAdd.getCheck_out().toString());

            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();
        } finally {
            try{
                if(preparedStatement != null) preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
                }
            }
        }



    // Controlla se una prenotazione è valida per il check_in e check_out scelti
    public static boolean validate(String check_in, String check_out, String tipo) throws SQLException, ParseException {
        ArrayList<PrenotazioneBean> validateList = PrenotazioneDAO.listOfBooked(check_in,check_out,tipo);
        if(validateList.size() == 0) return true; //Se la lista delle prenotazioni che da conflitto è vuota, allora la prenotazione si può effettuare

        //Se la lista delle prenotazioni che da conflitto nella data scelta, supera il numero di camere disponibili per il tipo scelto, allora la prenotazione non è valida
        else if(tipo.equals("standard") && validateList.size() < 10) return true;
        else if(tipo.equals("superior") && validateList.size() < 5) return true;
        else return tipo.equals("suite") && validateList.size() < 2;
    }



    // Ritorna la prima camera libera nel periodo scelto , -1 alrimenti(
    public static int getFirstFreeByType(String check_in, String check_out,String tipo) throws SQLException,ParseException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String selStatement = "select * from Camera where Camera.tipo = ?";
        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selStatement);
            preparedStatement.setString(1, tipo);
            ResultSet res = preparedStatement.executeQuery();
            List<Integer> numbers = new ArrayList<Integer>();
            while (res.next()){
                numbers.add(res.getInt("numero")); //Recupera i numeri di tutte le camere (Implementabile anche da parte)
            }

            ArrayList<PrenotazioneBean> listBooked = PrenotazioneDAO.listOfBooked(check_in, check_out, tipo);

            if(listBooked.size() == 0) return numbers.get(1); //Se la listOfBooked è vuota, ritorna la prima camera libera

            else {
                ArrayList<Integer> bookedNumber = new ArrayList<Integer>();
                for (PrenotazioneBean bean : listBooked) {
                    bookedNumber.add(bean.getNumero());  //Crea una lista di tutti i numeri di camere nella listOfBooked
                }

                for(Integer number :numbers){
                    if(!bookedNumber.contains(number)) return number; //Ritorna il primo numero di camera non contenuto nella listOfBooked
                }

            }
        }finally {
            try{
                if(preparedStatement != null) preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return -1;
    }


    //Ritorna una lista di tutte le prenotazioni che vanno in conflitto con i porametri passati
    public static ArrayList<PrenotazioneBean> listOfBooked(String check_in, String check_out, String tipo) throws SQLException, ParseException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        ArrayList<PrenotazioneBean> prenotazioneBeans = new ArrayList<PrenotazioneBean>();
        String statement = "SELECT Prenotazione.* from Prenotazione,Camera where Camera.numero = Prenotazione.numero and Camera.tipo = ?;";
        try{
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1, tipo);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                PrenotazioneBean prenotazioneBean = new PrenotazioneBean();
                prenotazioneBean.setNumero(resultSet.getInt("numero"));
                prenotazioneBean.setCodice_fiscale(resultSet.getString("codice_fiscale"));
                /*
                 QUANDO CHECK_IN E CHECK_OUT VENGONO RECUPERATI DAL DATABASE VENGONO SHIFTATI DI 1 ORA AVANTI -- RICONTROLLARE
                 */
                prenotazioneBean.setCheck_out(resultSet.getTimestamp("check_out"));
                prenotazioneBean.setCheck_in(resultSet.getTimestamp("check_in"));

                prenotazioneBeans.add(prenotazioneBean);
            }
            ArrayList<PrenotazioneBean> toReturn = new ArrayList<PrenotazioneBean>();

            for(PrenotazioneBean bean : prenotazioneBeans){
                Timestamp bcheckin = bean.getCheck_in();
                Timestamp bcheckout = bean.getCheck_out();

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                Date parsedDate1 = dateFormat.parse(check_in);
                Timestamp timestampCheckin = new Timestamp(parsedDate1.getTime());

                Date parsedDate2 = dateFormat.parse(check_out);
                Timestamp timestampCheckout = new Timestamp(parsedDate2.getTime());

                //Controlla se il check_in o il check_out inserito, coimbaciano con una fascia di prenotazione già esistente
                if(((timestampCheckout.after(bcheckin) && timestampCheckout.before(bcheckout)) || (timestampCheckin.after(bcheckin) && timestampCheckin.before(bcheckout)))) toReturn.add(bean);

                //Controlla se nella fascia temporale selezionata vi sono già prenotazioni esistenti
                else if((timestampCheckin.before(bcheckin) && timestampCheckout.after(bcheckout))) toReturn.add(bean);
            }
             return toReturn;
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
    }

    //





}
