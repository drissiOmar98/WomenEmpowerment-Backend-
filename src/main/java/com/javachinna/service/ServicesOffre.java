package com.javachinna.service;

import com.javachinna.model.*;
import com.javachinna.repo.RepoCandidacy;
import com.javachinna.repo.RepoOffers;
import com.javachinna.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class ServicesOffre implements IServices{

    @Autowired
    private RepoCandidacy repoCandidacy;
    @Autowired
    private RepoOffers repoOff;
    @Autowired
    private UserRepository repoUser;

    @Override
    public void updateOffer(Offres offer, Long idUser) {

        User user = repoUser.findById(idUser).orElse(null);
        offer.setUsers(user);
        repoOff.save(offer);

    }

    @Override
    public void  updateOffer(Offres offer){
        repoOff.save(offer);
    }

    @Override
    public void deleteOffer (Integer idOffer){
    Offres offres1 = repoOff.findById(idOffer).orElse(null);
        repoOff.delete(offres1);
    }

   @Override
    public List<Offres> GetOffer(){
    List<Offres> offres =(List<Offres>) repoOff.findAll();
    return  offres ;
   }
    @Override
    public void add(Candidacy candidacy,Integer idO, Long idU) {

        User user = repoUser.findById(idU).orElse(null);
        Offres offres = repoOff.findById(idO).orElse(null);

        candidacy.setOffers(offres);
        candidacy.setUsersW(user);

        repoCandidacy.save(candidacy);
    }

    @Override
    public void updateCandidacy (Candidacy candidacy){
        repoCandidacy.save(candidacy);
    }

    @Override
    public void deleteCandidacy (Integer idCandidacy){
        Candidacy candidacy1 = repoCandidacy.findById(idCandidacy).orElse(null);
        repoCandidacy.delete(candidacy1);
    }

    @Override
    public List<Candidacy> GetCandidacy() {
        List<Candidacy> candidacy = (List<Candidacy>) repoCandidacy.findAll();
        return  candidacy;
    }

    @Override
    public void addUser(User user) {
        repoUser.save(user);

    }


    @Override
    public List<Offres> OffresParDateCreation(Date date1, Date date2) {

        return repoOff.OffresParDateCreation(date1, date2);
    }
    @Override
    public  List<Candidacy> offerByProfession(Profession profession){
       // return repoCandidacy.offerByProfession(profession);
        return  null;
    }

    @Override
    public Integer getNumberOfUserInThisCandidacy(Integer idCandidacy) {
        return  repoOff.getNumberOfUserInThisCandidacy(idCandidacy);

    }

    @Override
    public Offres getOffresHighRecommended() {
        return null;
    }

  /*  @Override
    public Offres getOffresHighRecommended() {
        return null;
    }*/

  /*  @Override
    public Offres getOffresHighRecommended() {
        return null;
    }*/

    /*@Override
    public Offres getOffresHighRecommended() {
         Integer Max=0;
         Offres offres = new Offres();
        for (Offres o:repoOff.findAll() )
        {
            if (repoOff.getNumberOfUserInThisCandidacy(o.getIdOffer())>Max) {
                Max = repoOff.getNumberOfUserInThisCandidacy(o.getIdOffer());
            }

        }
        for(Offres o:repoOff.findAll() ) {

            if (repoOff.getNumberOfUserInThisCandidacy(o.getIdOffer()) == Max) {
                offres = repoOff.findById(o.getIdOffer()).orElse(null);
            }
        }
        return offres;
    }*/

    ///////////    new    12:30  Mahdi ///////////////
    @Override
    public List<Offres> listAllOffres(String keyword) {
        if (keyword != null) {
            return  repoOff.findAll(keyword);
        }
        return (List<Offres>) repoOff.findAll();
    }

    @Override
    public List<Candidacy> listAllCandidacy(String keyword) {
        if (keyword != null) {
            return  repoCandidacy.findAll(keyword);
        }
        return (List<Candidacy>) repoCandidacy.findAll();
    }

    @Override
    public List<Candidacy> CandidacyByProfession(Profession profession) {
        return repoCandidacy.offerByProfession(profession);}



   /* @Override
    public List<Offres> OffreByProfession(Profession profession){
        return repoOff.offerByProfession(profession);
    }*/
/*
    public List<Double> PourcentageReclamationByType() {
        List<Double> pourcentages=new ArrayList<Double>();
        double PUBLICATION = 0;
        double TRAINING=0;
        double OFFER=0;
        double CANDIDACY=0;

        List<Complaint> complaints=  (List<Complaint>) myReclamationRepository.findAll();
        System.out.println(complaints.toString());

        for (Complaint complaint: complaints) {


            if (complaint.getType().equals(TypeComplaint.PUBLICATION)) {
                PUBLICATION++;
            }


            else if (complaint.getType().equals(TypeComplaint.TRAINING)) {
                TRAINING++;}


            else if (complaint.getType().equals(TypeComplaint.OFFER)) {
                OFFER++;}

            else if (complaint.getType().equals(TypeComplaint.CANDIDACY)) {
                CANDIDACY++;}
        }
        if (complaints.size() !=0) {

            System.out.println("number_reclamations:"+complaints.size());

            PUBLICATION =  ((PUBLICATION/(complaints.size()))*100);


            TRAINING = ((TRAINING/(complaints.size()))*100);


            OFFER = ((OFFER/(complaints.size()))*100);

            CANDIDACY = ((CANDIDACY/(complaints.size()))*100);
        }
        pourcentages.add(PUBLICATION);

        pourcentages.add(TRAINING);

        pourcentages.add(OFFER);
        pourcentages.add(CANDIDACY);

        System.out.println(pourcentages);

        return pourcentages;
    }

    */

}
