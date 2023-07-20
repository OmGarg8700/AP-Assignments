import java.lang.ProcessBuilder.Redirect.Type;
import java.util.*;

class Placement_cell{
    private Vector<Company> company = new Vector<Company>();
    private Vector<Student> student = new Vector<Student>();

    private Date start_date_company;
    private Date end_date_company;

    private Date start_date_student;
    private Date end_date_student;

    // getter/setters
    public Date get_start_date_company() {
        return this.start_date_company;
    }

    public Date get_end_date_company() {
        return this.end_date_company;
    }

    public Date get_start_date_student() {
        return this.start_date_student;
    }

    public Date get_end_date_student() {
        return this.end_date_student;
    }

    public void add_students(Student s){
        this.student.addElement(s);
    }

    public void add_company(Company c){
        this.company.addElement(c);
    }

    public Vector<Company> get_registered_companies(){
        return this.company;
    }

    public Vector<Student> get_registered_students(){
        return this.student;
    }

    // Open student registrations
    public void Open_student_registrations(String a, String b){
        this.start_date_student = new Date(a);
        this.end_date_student = new Date(b);
    }

    // open company registrations
    public void Open_company_registrations(String a, String b){
        this.start_date_company = new Date(a);
        this.end_date_company = new Date(b);
    }

    public int get_number_students_registered(){
        return student.size();
    }
    
    public int get_number_company_registered(){
        return company.size();
    }


    public void get_number_offered_students(){
        int count = 0;
        for(int i=0;i<student.size();i++){
            if(student.get(i).get_student_status().equals("blocked") || student.get(i).get_offering_companies().size() > 0){
                count++;
                student.get(i).to_String();
                System.out.println("\n");
            }
        }

        System.out.println("Number of offered Students are: " + count + "\n");
    }

    public void get_student_details(String name, int roll){
        for(int i=0;i<student.size();i++){
            if(student.get(i).get_student_name() == name && student.get(i).get_student_roll() == roll){
                student.get(i).to_String();

                // companies applied and not applied
                Vector<Company> applied_companies = student.get(i).get_registered_companies();
                Vector<Company> not_applied_companies = new Vector<Company>();

                for(int j=0;j<company.size();j++){
                    boolean present = false;
                    for(int k=0;k<applied_companies.size();k++){
                        if(company.get(j).get_company_name() == applied_companies.get(k).get_company_name()){
                            present = true;
                        }

                    }
                    if(present == false){
                        not_applied_companies.addElement(company.get(k));
                    }
                }

                System.out.println("Applied Companies: ");
                for(int j=0;j<applied_companies.size();j++){
                    System.out.println(applied_companies.get(i).get_company_name());
                }
                System.out.println("Not Applied Companies");
                for(int j=0;j<not_applied_companies.size();j++){
                    System.out.println(not_applied_companies.get(i).get_company_name());
                }

                if(student.get(i).get_offering_companies().size() > 0){
                    System.out.println("Student is offered by companies");
                }
                else{
                    System.out.println("Student is not offered by any company");
                }

            }
        }
    }


    public void get_company_details(String name){
        for(int i=0;i<company.size();i++){
            if(company.get(i).get_company_name().equals(name)){
                company.get(i).toString();
                System.out.println("\n");

                // offered students
                Vector<Student> off_student = company.get(i).get_offered_Students();
                System.out.println("Offered Students: \n");
                for(int j=0;j<off_student.size();j++){
                    System.out.println("Student Name: " + off_student.get(j).get_student_name());
                    System.out.println("Student Roll: " + off_student.get(j).get_student_roll());
                    System.out.println("\n");
                }

                break;
            }
        }

        System.out.println("\n");
    }

    public void get_avg_package(){
        int final_package = 0;
        int count = 0;
        for(int i=0;i<student.size();i++){
            if(student.get(i).get_student_status().equals("placed")){
                final_package += student.get(i).get_student_ctc();
                count++;
            }
        }

        System.out.println("The Average of this year is: " + final_package/count);
    }

    public void get_company_results(String name){
        for(int i=0;i<company.size();i++){
            if(company.get(i).get_company_name().equals(name)){
                System.out.println("Following are the students selected:");
                Vector<Student> ac_stud = company.get(i).get_accepted_students();

                for(int j=0;j<ac_stud.size();j++){
                    ac_stud.get(j).to_String();
                    System.out.println("\n");
                }
            }
        }
    }
}

class Company{
    private Vector<Student> registeredstudent = new Vector<Student>();
    private Vector<Student> offered_student = new Vector<Student>();
    private Vector<Student> accepted_student = new Vector<Student>();

    private String Company_name;
    private String Company_role;
    private double Company_package;
    private double Company_cgpa;
    private Date Company_date_time;
    private double ctc_offered;
    private int postions;   // to be discussed

    private boolean is_registered;

    // constructor class is always made
    public Company(String name, String role, double cgpa, double packag){
        // this.Company_date_time = date_time; *****
        this.Company_name = name;
        this.Company_package = packag;
        this.Company_role = role;
        this.Company_cgpa = cgpa;
        this.is_registered = false;
        this.postions = 0;
    }

    // get/set functions
    public String get_company_name(){
        return this.Company_name;
    }
    public String get_company_role(){
        return this.Company_role;
    }
    public double get_company_package(){
        return this.Company_package;
    }
    public double get_company_cgpa(){
        return this.Company_cgpa;
    }
    public double get_company_ctc(){
        return this.ctc_offered;
    }
    public Vector<Student> get_accepted_students(){
        return this.accepted_student;
    }
    public Vector<Student> get_offered_Students(){
        return this.offered_student;
    }

    public void add_register_company(Student s){
        this.registeredstudent.addElement(s);
    }

    public void add_accepted_company(Student s){
        this.accepted_student.addElement(s);
    }
    public void add_position(){
        this.postions += 1;
    }

    // Update role/ package/ CGPA
    public void set_company_cgpa(double cgpa){
        this.Company_cgpa = cgpa;
        return;
    }
    public void set_company_role(String role){
        this.Company_role = role;
        return;
    }public void set_company_package(double packag){
        this.Company_package = packag;
        return;
    }

    // functions

    public void to_String(){
        System.out.println("CompanyName: " + this.Company_name);
        System.out.println("Company Role offering: " + this.Company_role);
        System.out.println("Company Package: " + this.Company_package);
        System.out.println("Company CGPA criteria: " + this.Company_cgpa);
    }

    public void register_institute_drive(Placement_cell p, String s){
        // only one time registration
        this.Company_date_time = new Date(s);
        if(this.is_registered == false && this.Company_date_time.compareTo(p.get_start_date_company()) > 0 && this.Company_date_time.compareTo(p.get_end_date_company()) < 0){
            
            System.out.println("Registered!!");
            this.is_registered = true;
            p.add_company(this);
        }

    }

    public void getSelectedStudents(){
        if(this.is_registered == true){
            for (int i = 0; i < offered_student.size(); i++) {
                offered_student.get(i).to_String();
                System.out.println("\n");
            }
        }
    }


    // *** This is to be called once the student registrations are ended ***
    public void offer_students(Student s){
        // For now I am taking first 2 students who registered
        if(this.is_registered == true){
            // if(registeredstudent.size() < 2){
            //     for(int i=0;i<registeredstudent.size();i++){
            //         // make offer
            //         registeredstudent.get(i).add_offering_companies(this);
            //         this.offered_student.addElement(registeredstudent.get(i));
            //     }
            // }
            // else{
            //     for(int i=0;i<2;i++){
            //         // make offer
            //         registeredstudent.get(i).add_offering_companies(this);
            //         this.offered_student.addElement(registeredstudent.get(i));
            //     }
            // }
            if(this.postions < 2){
                s.add_offering_companies(this);
                this.offered_student.addElement(s);
            }
        }
    }
     
}

class Student{
    private Vector<Company> available_comp = new Vector<Company>();
    private Vector<Company> registered_comp = new Vector<Company>();

    private Vector<Company> offering_comp = new Vector<Company>();
    private Vector<Company> rejected_comp = new Vector<Company>();

    private String Student_name;
    private int Student_roll;
    private double Student_cgpa;
    private String Student_branch;
    private double highest_ctc;
    private Date Student_date_time;

    private String status;
    private String offer;

    // boolean registered_placement;

    // constructor
    public Student(String name, int roll, double cgpa, String branch, Placement_cell p){
        this.Student_name = name;
        this.Student_roll = roll;
        this.Student_cgpa = cgpa;
        this.Student_branch = branch;

        this.highest_ctc = 0;
        this.status = "not-applied";
        this.offer = "not-placed";

        // now appending available companies
        Vector<Company> comp = p.get_registered_companies();
        for(int i=0;i<comp.size();i++){
            if(this.Student_cgpa >= comp.get(i).get_company_cgpa() &&  comp.get(i).get_company_ctc() >= 3*this.highest_ctc){
                this.available_comp.addElement(comp.get(i));
            }
        }
    }

    // getter/setter functions
    public String get_student_name(){
        return this.Student_name;
    }
    public int get_student_roll(){
        return this.Student_roll;
    }
    public double get_student_cgpa(){
        return this.Student_cgpa;
    }
    public String get_student_branch(){
        return this.Student_branch;
    }
    public double get_student_ctc(){
        return this.highest_ctc;
    }
    public String get_student_status(){
        return this.offer;          // returning whether a student is placed or not-placed or blocked
    }

    public Vector<Company> get_offering_companies(){
        return this.offering_comp;
    }
    public Vector<Company> get_registered_companies() {
        return this.registered_comp;
    }
    public void add_offering_companies(Company c){
        this.offering_comp.addElement(c);
    }

    public void to_String(){
        System.out.println("Student Name: " + this.Student_name);
        System.out.println("Student roll: " + this.Student_roll);
        System.out.println("Student branch: " + this.Student_branch);
        System.out.println("Student CGPA: " + this.Student_cgpa);
    }

    // Register for placement drive
    public void Student_register_drive(Placement_cell p, String s){
        this.Student_date_time = new Date(s);
        if(this.status.equals("not-applied") && this.Student_date_time.compareTo(p.get_start_date_student()) > 0 && this.Student_date_time.compareTo(p.get_end_date_student()) < 0){
            p.add_students(this);
            // *** date how to print ***
            System.out.println(this.Student_name + "Registered for Placement Drive att IIITD \n Your details are: \n");
            System.out.println("Name: " + this.Student_name);
            System.out.println("Roll No: " + this.Student_roll);
            System.out.println("CGPA: " + this.Student_cgpa);
            System.out.println("Branch: " + this.Student_branch);

            this.status = "applied";
            
        }
    }


    // Register For Company
    // functionality blocked for status: placed
    public void Student_register_company(String name){
        // student should be registered for placement drive
        if(this.status.equals("applied") && this.offer.equals("not-placed")){
            for(int i=0;i<available_comp.size();i++){
                if(available_comp.get(i).get_company_name().equals(name)){

                    available_comp.get(i).add_register_company(this);
                    available_comp.get(i).add_position();
                    available_comp.get(i).offer_students(this);

                    System.out.println("Successfully registered for" + available_comp.get(i).get_company_role() + "Role at " + available_comp.get(i).get_company_name());
                }
            }
        }
    }

    // Get All available companies
    // functionality blocked for status: placed
    public void get_available_companies(){
        if(this.offer.equals("not-placed")){
            for(int i=0;i<available_comp.size();i++){
                available_comp.get(i).to_String();
                System.out.println("\n");
            }
        }
        else{
            System.out.println("unavailable");
        }
    }
    // Get Current Status
    public void get_current_status(){
        if(this.status.equals("applied")){
            if(status.equals("blocked")){
                System.out.println("Sorry your status is blocked");
            }
            else if(status.equals("placed")){
                System.out.println("You are placed already !!");
            }
            else{
                int index = -1;
                int max = 0;
                for(int i=0;i<offering_comp.size();i++){
                    if(offering_comp.get(i).get_company_package() > max){
                        index = i;
                    }
                }
    
                if(index != -1){
                    Company c = offering_comp.get(index);
    
                    System.out.println("You have been offered by " + c.get_company_name() + " !! Please accept the offer.");
                }
                else{
                    System.out.println("You have no offer till now");
                }
    
            }
        }
    }

    // Update CGPA
    public void update_cgpa(double cgpa){
        this.Student_cgpa = cgpa;
    }

    // Accept / Reject offer
    // functionality blocked for status: placed
    public void accept_offer(){
        if(offer.equals("not-placed") && offering_comp.size() > 0 && this.status.equals("applied")){
            int index = -1;
            int max = 0;
            for(int i=0;i<offering_comp.size();i++){
                if(offering_comp.get(i).get_company_package() > max){
                    index = i;
                }
            }

            if(index != -1){
                // now we have company
                Company c = offering_comp.get(index);
                // c.postions -= 1;

                // add to company 
                c.add_accepted_company(this);

                // remove all elements from offering companies and all appending offered companies
                this.offering_comp.clear();
                this.offering_comp.addElement(c);

                this.offer = "placed";
                this.highest_ctc = c.get_company_package();

                System.out.println("Congratulations" + this.Student_name + " You have accepted the offer by" + c.get_company_name() + "!!");
            }
            else{
                System.out.println("You have no offer till now");
            }
        }
        else{
            System.out.println("unavailable");
        }
    }

    // functionality blocked for status: placed
    public void reject_offer(String name){
        if(status.equals("not-placed") && this.status.equals("applied")){
            int index = -1;
            for(int i=0;i<offering_comp.size();i++){
                if(offering_comp.get(i).get_company_name().equals(name)){
                    index = i;
                    break;
                }
            }

            // now removing from offering companies and adding to rejected companies
            if(index != -1){
                rejected_comp.addElement(offering_comp.get(index));
                offering_comp.remove(index);
            }
            
            if(offering_comp.size() == 0 && rejected_comp.size() > 0){
                status = "blocked";
            }
        }
        else{
            System.out.println("unavailable");
        }
    }
}

public class assignment1 {

    public static void main(String[] args) {
        Scanner sc1 = new Scanner(System.in);       // for numbers
        Scanner sc2 = new Scanner(System.in);       // for inputs

        Placement_cell P = new Placement_cell();
        Vector<Student> av_Students = new Vector<Student>();
        Vector<Company> av_Companies = new Vector<Company>();

        int choice;

        while(true){
            System.out.println("Welcome to FutureBuilder: \n \t 1) Enter the Application \n \t 2) Exit the Application \n");
            choice = sc1.nextInt();

            if(choice == 1){
                while(true){
                    System.out.println("Choose The mode you want to Enter in:- \n \t 1) Enter as Student Mode \n \t 2) Enter as Company Mode \n \t 3) Enter as Placement Cell Mode \n \t 4) Return To Main Application \n");
                    choice = sc1.nextInt();

                    if(choice == 1){
                        while(true){
                            System.out.println("Choose the Student Query to perform- \n \t 1) Enter as a Student(Give Student Name, and Roll No.) \n \t 2) Add students \n \t 3) Back \n");
                            choice = sc1.nextInt();

                            if(choice == 1){
                                String name = sc2.nextLine();
                                int roll = sc1.nextInt();

                                Student new_s = null;
                                for(int i=0;i<av_Students.size();i++){
                                    if(av_Students.get(i).get_student_name().equals(name) && av_Students.get(i).get_student_roll() == roll){
                                        new_s = av_Students.get(i);
                                        break;
                                    }
                                }
                                
                                if(new_s != null){
                                    while(true){
                                        System.out.println("Welcome " + new_s.get_student_name() + "\n \t 1) Register For Placement Drive \n \t 2) Register For Company \n \t 3) Get All available companies \n \t 4) Get Current Status \n \t 5) Update CGPA \n \t 6) Accept offer \n \t 7) Reject offer \n \t 8) Back \n");
                                        choice = sc1.nextInt();

                                        if(choice == 1){
                                            String date = sc2.nextLine();
                                            new_s.Student_register_drive(P, date);
                                        }
                                        else if(choice == 2){
                                            String comp_name = sc2.nextLine();
                                            new_s.Student_register_company(comp_name);
                                        }
                                        else if(choice == 3){
                                            new_s.get_available_companies();
                                        }
                                        else if(choice == 4){
                                            new_s.get_current_status();
                                        }
                                        else if(choice == 5){
                                            double cgpa = sc1.nextDouble();
                                            new_s.update_cgpa(cgpa);
                                        }
                                        else if(choice == 6){
                                            new_s.accept_offer();
                                        }
                                        else if(choice == 7){
                                            String cname = sc2.nextLine();
                                            new_s.reject_offer(cname);
                                        }
                                        else{
                                            break;
                                        }
                                    }
                                }
                                else{
                                    System.out.println("Could not find Student with the given name, try to add the Student first");
                                }

                            }
                            else if(choice == 2){
                                System.out.println("Number of students to add: ");
                                int number = sc1.nextInt();

                                for(int i=0;i<number;i++){
                                    String name = sc2.nextLine();
                                    int roll = sc1.nextInt();
                                    double cgpa = sc1.nextDouble();
                                    String branch = sc2.nextLine();

                                    Student s = new Student(name, roll, cgpa, branch, P);

                                    av_Students.addElement(s);
                                    System.out.println("\n");
                                }
                            }
                            else{
                                break;
                            }
                        }
                    }
                    else if(choice == 2){
                        while(true){
                            System.out.println("Choose the Company Query to perform- \n \t 1) Add Company and Details \n \t 2) Choose Company \n \t 3) Get Available Companies \n \t 4) Back \n");
                            choice = sc1.nextInt();

                            if(choice == 1){
                                String name = sc2.nextLine();
                                String role = sc2.nextLine();
                                double packag = sc1.nextDouble();
                                double cgpa = sc1.nextDouble();

                                Company c = new Company(name, role, cgpa, packag);

                                // adding in av_companies
                                av_Companies.addElement(c);
                            }
                            else if(choice == 2){
                                System.out.println("Choose To enter into mode of Available Companies:- \n");
                                String name = sc2.nextLine();

                                Company new_c = null;
                                
                                for(int i=0;i<av_Companies.size();i++){
                                    if(av_Companies.get(i).get_company_name().equals(name)){
                                        new_c = av_Companies.get(i);
                                        break;
                                    }
                                }


                                if(new_c != null){
                                    while(true){
                                        System.out.println("Welcome " + new_c.get_company_name() + "\n \t 1) Update Role \n \t 2) Update Package \n \t 3) Update CGPA criteria \n \t 4) Register To Institute Drive \n \t 5) Back \n");
                                        choice = sc1.nextInt();
    
                                        if(choice == 1){
                                            String role = sc2.nextLine();
                                            new_c.set_company_role(role);
                                        }
                                        else if(choice == 2){
                                            double packag = sc1.nextDouble();
                                            new_c.set_company_package(packag);
                                        }
                                        else if(choice == 3){
                                            double cgpa = sc1.nextDouble();
                                            new_c.set_company_cgpa(cgpa);
                                        }
                                        else if(choice == 4){
                                            String date = sc2.nextLine();
                                            new_c.register_institute_drive(P, date);
                                        }
                                        else{
                                            break;
                                        }
                                    }
                                }
                                else{
                                    System.out.println("Could not find Company with the given name, try to add the company first");
                                }
                            }
                            else if(choice == 3){
                                for(int i=0;i<av_Companies.size();i++){
                                    System.out.println(av_Companies.get(i).get_company_name());
                                }
                            }
                            else{
                                break;
                            }
                        }
                    }
                    else if(choice == 3){
                        while(true){
                            System.out.println("Welcome to IIITD Placement Cell \n \t 1) Open Student Registrations \n \t 2) Open Company Registrations \n \t 3) Get Number of Student Registrations \n \t 4) Get Number of Company Registrations \n \t 5) Get Number of Offered/Unoffered/Blocked Students \n \t 6) Get Student Details \n \t 7) Get Company Details \n \t 8) Get Average Package \n \t 9) Get Company Process Results \n \t 10) Back \n");
                            choice = sc1.nextInt();

                            if(choice == 1){
                                System.out.println("Fill in the details:- \n \t 1) Set the Opening time for Student registrations \n \t 2) Set the Closing time for Student registrations");
                                String a = sc2.nextLine();
                                String b = sc2.nextLine();

                                P.Open_student_registrations(a, b);

                            }
                            else if(choice == 2){
                                System.out.println("Fill in the details:- \n \t 1) Set the Opening time for Company registrations \n \t 2) Set the Closing time for Company registrations");
                                String a = sc2.nextLine();
                                String b = sc2.nextLine();

                                P.Open_company_registrations(a, b);
                            }
                            else if(choice == 3){
                                System.out.println("Number of Student registrations: " + P.get_number_students_registered());
                            }
                            else if(choice == 4){
                                System.out.println("Number of Company registrations: " + P.get_number_company_registered());
                            }
                            else if(choice == 5){
                                P.get_number_offered_students();
                            }
                            else if(choice == 6){
                                String name = sc2.nextLine();
                                int roll = sc1.nextInt();

                                P.get_student_details(name, roll);
                            }
                            else if(choice == 7){
                                String name = sc2.nextLine();
                                P.get_company_details(name);
                            }
                            else if(choice == 8){
                                P.get_avg_package();
                            }
                            else if(choice == 9){
                                String name = sc2.nextLine();
                                P.get_company_results(name);
                            }
                            else{
                                break;
                            }
                        }
                    }
                    else{
                        break;
                    }
                }
            }
            else{
                break;
            }
        }

        System.out.println("Thanks for Using FutureBuilder !!!!");
        sc1.close();
        sc2.close();
        
    }

    
}

// compoany: when marking offered change the hisghest ctc of student at every point : this i will think later
