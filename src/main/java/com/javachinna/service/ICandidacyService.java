package com.javachinna.service;

import com.javachinna.model.CandidacyUniversity;
import com.javachinna.model.PartnerInstitution;
import com.javachinna.model.StatusOfCandidacy;

import java.util.Date;
import java.util.List;

public interface ICandidacyService {
    public void addDemandAndAssignToStudent(StatusOfCandidacy status, Date DateOFCandidacy, Long idUser);
    public void deleteCandidacyById(Integer Id);
    public void unassignStudentDemand(Integer id);
    public void addDemandAndAssignToStudentAndUniversity(CandidacyUniversity demand, Long idUser, Integer idPartner);
    //public void uploadToLocal(MultipartFile file) ;
    //public void uploadToDb(MultipartFile file) ;
    public List<CandidacyUniversity> getAllAcceptedDemandByUniversity(Integer idUniversity, StatusOfCandidacy status);
    public int getNbrDemandByUniversity(Integer idUniversity);
    public List<CandidacyUniversity> getdemandsByUniversity(Integer idUniversity);
    public List<CandidacyUniversity> getdemandsByStudent(Long idStudent);
    public List<CandidacyUniversity>getAllDemands();

    public int countAcceptedDemandsByUniversity(Integer idUniversity );
    public void AcceptDemand(Integer idDemand);
    public void accept(CandidacyUniversity d , Integer id);
    public void rejectDemand(Integer idDemand);
    public List<CandidacyUniversity>demandByDateOfCreation(Date date1, Date date2);
    public List<CandidacyUniversity>findAllByStatus(StatusOfCandidacy status);
    public List<Object>countDemandsByDate();
    public List<Object[]> countAcceptedDemandByDate();
    public List <Object[]> countDemandByUniversity();
    //List<Object[]> countNumberStudentPerNationalityByYear(String ch,Date dateDebut,Date dateFin);
    public long getTimeAttendForDemandResponse(int idDemand);

    public long getAverageWaitingTimeDemandByUniversity(Integer idUniversity);

    public void addCvToCandidacy(String id,Integer idDemand);


    public boolean existId(Integer idDemand);
    public CandidacyUniversity getdemandById(Integer IdDemand);



}
