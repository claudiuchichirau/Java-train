package org.example.model;

import java.util.*;
import java.util.stream.Collectors;

public class Catalog {
    private static Catalog instance = null;
    private Map<UUID, PairInfo> gradeMap = new HashMap<>();
    private Set<Student> students = new HashSet<>();
    private Set<Course> courses = new HashSet<>();
    private List<Review> studentsReviews = new ArrayList<>();
    private TreeMap<Float, UUID> professorRatings = new TreeMap<>(Collections.reverseOrder());

    private Catalog() { }

    public static Catalog getInstance(String key) {
        if(Menu.getProfessorKeys().contains(key)) {
            if(instance == null) {
                instance = new Catalog();
            }
            return instance;
        } else{
            throw new SecurityException("Access denied: Invalid key");
        }
    }

    public void addGrade(User user, UUID courseId, PairInfo pairInfo) {
        if (AccessControl.isProfesor.test(user)) {
            if(verifyCoursesTypeAccess(courseId, pairInfo)) {
                gradeMap.put(courseId, pairInfo);
                System.out.println("Nota a fost adăugată cu succes.");
            } else {
                throw new SecurityException("Acces interzis: Studentii pot fi inscrisi la maxim 2 cursuri Optionale si 2 Facultative.");
            }
        } else {
            throw new SecurityException("Acces interzis: Doar utilizatorii cu rolul de 'Profesor' pot adauga note.");
        }
    }

    private boolean verifyCoursesTypeAccess(UUID courseId, PairInfo pairInfo) {
        int facultativeCourses = 0;
        int optionalCourses = 0;

        UUID studentId = pairInfo.getStudentId();
        Student student = getStudentById(studentId);
        Course course = getCourseById(courseId);

        if (student == null) {
            throw new IllegalArgumentException("Studentul nu există.");
        }
        if (course == null) {
            throw new IllegalArgumentException("Cursul nu există.");
        }

        for (Map.Entry<UUID, PairInfo> entry : gradeMap.entrySet()) {
            if (entry.getValue().getStudentId().equals(studentId)) {
                Course currentCourse = getCourseById(entry.getKey());

                if (currentCourse != null) {
                    if (currentCourse.getCourseType() == CourseType.FACULTATIVE) {
                        facultativeCourses++;
                    } else if (currentCourse.getCourseType() == CourseType.OPTIONAL) {
                        optionalCourses++;
                    }
                }
            }
        }

        if (course.getCourseType() == CourseType.FACULTATIVE && facultativeCourses >= 2) {
            System.out.println("Studentul este deja înscris la 2 cursuri facultative.");
            return false;
        } else if (course.getCourseType() == CourseType.OPTIONAL && optionalCourses >= 2) {
            System.out.println("Studentul este deja înscris la 2 cursuri opționale.");
            return false;
        }
        return true;
    }

    public Student getStudentById(UUID studentId) {
        Set<Student> copyStudents = students;

        return students.stream()
                .filter(student -> student.getUserId().equals(studentId))
                .findFirst()
                .orElse(null);
    }

    public Course getCourseById(UUID courseId) {
        return courses.stream()
                .filter(course -> course.getCourseId().equals(courseId))
                .findFirst()
                .orElse(null);
    }

    public PairInfo getPairInfo(UUID courseId) {
        return gradeMap.get(courseId);
    }

    public Set<Student> getStudents() {
        return students;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public boolean existsStudent(String studentFirstNames, String studentLastName) {
        List<String> firstNamesList = Arrays.asList(studentFirstNames.split("-"));

        for (Student student : students) {
            // Verificăm dacă lista de prenume și numele de familie se potrivesc
            if (student.getFirstNames().equals(firstNamesList) && student.getLastName().equalsIgnoreCase(studentLastName)) {
                return true;
            }
        }
        return false;
    }

    public boolean existsCourse(String courseName) {
        for (Course course: courses) {
            if (course.getCourseName().equals(courseName)) {
                return true;
            }
        }
        return false;
    }

    public void addStudent(Student student, User user) {
        if (AccessControl.canAddStudent.test(user)) {
            students.add(student);

            System.out.println("Studentul " + student.getFirstNames() + " " + student.getLastName() + " a fost adaugat cu succes.");
        } else {
            throw new SecurityException("Acces interzis: Doar utilizatorii cu rolul de 'Secretar' pot adauga noi studenti.");
        }
    }

    public void addCourse(Course course, User user) {
        if (AccessControl.isSecretar.test(user)) {
            courses.add(course);
            System.out.println("Cursul " + course.getCourseName() + " a fost adăugat cu succes.");
        } else {
            throw new SecurityException("Acces interzis: Doar utilizatorii cu rolul de 'Secretar' pot adauga noi cursuri.");
        }
    }

    public Set<Student> getStudentsByCourseType(CourseType courseType) {
        Set<Student> studentsByCourseType = new HashSet<>();
        for (Map.Entry<UUID, PairInfo> entry : gradeMap.entrySet()) {
            Course course = getCourseById(entry.getKey());
            if (course.getCourseType().equals(courseType)) {
                studentsByCourseType.add(getStudentById(entry.getValue().getStudentId()));
            }
        }
        return studentsByCourseType;
    }

    public double calculateAverageGrade(User user, StudyYear yearOfStudy) {
        if (user instanceof Student) {
            return calculateAverageForStudent((Student) user);
        } else if (AccessControl.isProfesor.test(user) || AccessControl.isSecretar.test(user)) {
            return calculateAverageForYear(yearOfStudy);
        } else {
            throw new SecurityException("Acces interzis: Nu aveti permisiunea de a vedea mediile.");
        }
    }

    private double calculateAverageForStudent(Student student) {
        double sum = 0;
        int count = 0;
        for (Map.Entry<UUID, PairInfo> entry : gradeMap.entrySet()) {
            if (entry.getValue().getStudentId().equals(student.getUserId())) {
                sum += entry.getValue().getGrade();
                count++;
            }
        }
        if (count != 0){
            return sum / count;
        } else {
            return 0;
        }
    }

    private double calculateAverageForYear(StudyYear yearOfStudy) {
        double sum = 0;
        int count = 0;
        for (Student student : students) {
            if (student.getStudyYear() == yearOfStudy) {
                sum += calculateAverageForStudent(student);
                count++;
            }
        }
        return (count > 0) ? sum / count : 0;
    }

    public void modifyGrade(User user, UUID studentId, UUID courseId, double newGrade) {
        if (AccessControl.isSecretar.test(user) || AccessControl.isProfesor.test(user)) {
            Map<ExamType, Double> exams = Sesiune.getExamsForStudent(studentId);
            boolean canModify = exams.containsKey(ExamType.EXAMEN_MARIRE) ||
                    exams.containsKey(ExamType.EXAMEN_RESTANTA);

            if (canModify) {
                PairInfo pairInfo = gradeMap.get(courseId);
                if (pairInfo != null && pairInfo.getStudentId().equals(studentId)) {
                    gradeMap.put(courseId, new PairInfo(studentId, newGrade));
                    System.out.println("Nota a fost modificată cu succes.");
                } else {
                    throw new IllegalArgumentException("Studentul sau cursul nu există.");
                }
            } else {
                throw new SecurityException("Acces interzis: Nota poate fi modificată doar în sesiunea de mariri sau restante.");
            }
        } else {
            throw new SecurityException("Acces interzis: Doar profesorii și secretarii pot modifica notele.");
        }
    }

    public void evalueazaStudentii() {
        for (Student student : students) {
            double total = 0;
            int count = 0;

            for (Double grade : Sesiune.getExamsForStudent(student.getUserId()).values()) {
                total += grade;
                count++;
            }

            double average = count > 0 ? total / count : 0;
            System.out.println("Media studentului " + student.getFirstNames() + " " + student.getLastName() + " este: " + average);
        }
    }

    public void evalueazaSesiune(User user) {
        if (AccessControl.isProfesor.test(user)) {
            Map<UUID, Double> studentAverages = new HashMap<>();

            for (Map.Entry<UUID, Map<ExamType, Double>> entry : Sesiune.getAllExams().entrySet()) {
                UUID studentId = entry.getKey();
                Map<ExamType, Double> exams = entry.getValue();
                double total = 0;
                int count = 0;

                for (Double grade : exams.values()) {
                    total += grade;
                    count++;
                }

                double average = count > 0 ? total / count : 0;
                studentAverages.put(studentId, average);
            }

            List<Map.Entry<UUID, Double>> sortedStudents = studentAverages.entrySet().stream()
                    .sorted(Map.Entry.<UUID, Double>comparingByValue().reversed())
                    .collect(Collectors.toList());

            System.out.println("Medii pe studenti in ordinea descrescatoare:");
            for (Map.Entry<UUID, Double> entry : sortedStudents) {
                Student student = getStudentById(entry.getKey());
                System.out.println("Student: " + student.getFirstNames() + " " + student.getLastName() + " - Media: " + entry.getValue());
            }
        } else {
            throw new SecurityException("Acces interzis: Doar profesorii pot evalua sesiunile.");
        }
    }

    public void addReview(Student student, UUID professorId, String question, int rating) {
        if (AccessControl.isStudent.test(student)) {
            Review review = new Review(professorId, student.getUserId(), question, rating);
            studentsReviews.add(review);
            System.out.println("Review-ul a fost adăugat cu succes.");
        } else {
            throw new SecurityException("Acces interzis: Doar studenții pot acorda review-uri.");
        }
    }

    public void viewReviews(User user, UUID professorId) {
        if (AccessControl.isSecretar.test(user)) {
            List<Review> reviews = studentsReviews.stream()
                    .filter(review -> review.getProfessorId().equals(professorId))
                    .collect(Collectors.toList());
            System.out.println("Review-uri pentru profesorul " + professorId + ":");
            for (Review review : reviews) {
                System.out.println(review);
            }
        } else {
            throw new SecurityException("Acces interzis: Doar secretarii pot citi review-urile.");
        }
    }

    private void updateCourseTypesBasedOnProfessorReviews() {
        Map<UUID, List<Review>> reviewsByProfessor = studentsReviews.stream()
                .collect(Collectors.groupingBy(Review::getProfessorId));

        for (Map.Entry<UUID, List<Review>> entry : reviewsByProfessor.entrySet()) {
            UUID professorId = entry.getKey();
            List<Review> reviews = entry.getValue();

            double averageRating = reviews.stream()
                    .mapToInt(Review::getRating)
                    .average()
                    .orElse(0.0);

            CourseType newType = determineCourseTypeBasedOnRating(averageRating);

            for (Course course : courses) {
                if (course.getProfessorId().equals(professorId)) {
                    course.setCourseType(newType);
                }
            }
        }
    }

    private CourseType determineCourseTypeBasedOnRating(double averageRating) {
        if (averageRating <= 1) {
            return CourseType.FACULTATIVE;
        } else if (averageRating >= 5) {
            return CourseType.OPTIONAL;
        }
        return CourseType.MANDATORY;
    }

    public void displayProfessorRanking() {
        System.out.println("Clasament profesori in functie de nota primita:");
        professorRatings.forEach((rating, professorId) -> {
            String professorName = getProfessorName(professorId);
            System.out.println("Profesor: " + professorName + ", Nota: " + rating);
        });
    }

    private String getProfessorName(UUID professorId) {
        Course course = courses.stream()
                .filter(c -> c.getProfessorId().equals(professorId))
                .findFirst()
                .orElse(null);

        if (course != null) {
            return course.getCourseName();
        } else {
            return "Unknown Professor";
        }
    }
}