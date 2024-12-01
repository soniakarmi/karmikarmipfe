package com.example.Backend.Controllers;

import com.example.Backend.Dto.Requests.QuizResponseRequest;
import com.example.Backend.Dto.Responses.QuizResult;
import com.example.Backend.Entities.*;
import com.example.Backend.Repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Backend.Utility.GenralUtilities;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class QuizResponseController {
    private final UtilisateurRepository utilisateurRepository;
    private final SupportCoursRepository supportCoursRepository;
    private final QuizPropositionRepository quizPropositionRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuestionResponseRepository questionResponseRepository;
    private final NoteRepository noteRepository;
    private final QuizFormRepository quizFormRepository;

    @PostMapping("/submit/{quizFormId}")
    public ResponseEntity<?> submitQuiz(@PathVariable Long quizFormId, @RequestBody List<QuizResponseRequest> reponses) {
        Utilisateur etudiantOpt = GenralUtilities.getAuthenticatedUser();

        if (!(etudiantOpt instanceof Etudiant)) {
            return ResponseEntity.status(403).body("L'utilisateur authentifié n'est pas un étudiant.");
        }

        Optional<QuizForm> quizFormOpt = quizFormRepository.findById(quizFormId);
        if (quizFormOpt.isPresent()) {
            Etudiant etudiant = (Etudiant) etudiantOpt;
            QuizForm quizForm = quizFormOpt.get();
            float totalPoints = 0;
            float earnedPoints = 0;

            for (QuizResponseRequest reponse : reponses) {
                QuizProposition proposition = quizPropositionRepository.findById(reponse.getPropositionResponse())
                        .orElseThrow(() -> new RuntimeException("Proposition introuvable pour l'ID " + reponse.getPropositionResponse()));

                QuizQuestion question = quizQuestionRepository.findById(reponse.getQuestion())
                        .orElseThrow(() -> new RuntimeException("Question introuvable pour l'ID " + reponse.getQuestion()));

                QuizResponse questionResponse = QuizResponse.builder()
                        .etudiant(etudiant)
                     //  .QuizProposition(proposition)// Corrigé ici
                        .question(question)
                        .build();

                questionResponseRepository.save(questionResponse);

                if (proposition.getId().equals(question.getResponseCorrectId())) {
                    earnedPoints += question.getPoints();
                }
                totalPoints += question.getPoints();
            }

            Note note = Note.builder()
                    .note(earnedPoints)
                    .quizForm(quizForm)
                    .etudiant(etudiant)
                    .build();
            noteRepository.save(note);

            QuizResult quizResult = QuizResult.builder()
                    .note(earnedPoints)
                    .phrase("Votre score est " + earnedPoints + " / " + totalPoints)
                    .build();

            return ResponseEntity.ok(quizResult);
        } else {
            return ResponseEntity.status(404).body("Quiz introuvable pour l'ID " + quizFormId);
        }
    }
}



                //  Optional<SupportCours> supportCoursOpt = supportCoursRepository.findById(quizFormId);
       // System.out.println("hello");
        //if ( supportCoursOpt.isPresent()) {
          //  Etudiant etudiant = (Etudiant) etudiantOpt;
            //QuizForm quizForm = (QuizForm) supportCoursOpt.get();

            //float totalPoints = 0;
           // float earnedPoints = 0;

        //    for (QuizResponseRequest reponse : reponses) {
          //      QuizProposition proposition = quizPropositionRepository.findById(reponse.getPropositionResponse())
            //            .orElseThrow(() -> new RuntimeException("Proposition introuvable"));
              //  QuizQuestion question = quizQuestionRepository.findById(reponse.getQuestion())
                //        .orElseThrow(() -> new RuntimeException("Question introuvable"));

                //QuizResponse questionResponse = QuizResponse.builder()
                  //      .etudiant(etudiant)
                    //    .QuizProposition(proposition)
                      //  .question(question)
                        //.build();

               // questionResponseRepository.save(questionResponse);

              //  if (proposition.getId().equals(question.getResponseCorrectId())) {
                 //   earnedPoints += question.getPoints();
                //}
                //totalPoints += question.getPoints();
          //  }
            //Note note = Note.builder()
              //      .note(earnedPoints)
                //    .quizForm(quizForm)
                  //  .etudiant(etudiant)
                    //.build();
           // noteRepository.save(note);

          //  QuizResult quizResult = QuizResult.builder()
            //        .note(earnedPoints)
              //      .phrase("Votre score est " + earnedPoints + " / " + totalPoints)
                //    .build();

           // return ResponseEntity.ok(quizResult);
       // } else {
         //   return ResponseEntity.status(404).body("Étudiant ou Quiz introuvable");
        //}
   // }
//}
