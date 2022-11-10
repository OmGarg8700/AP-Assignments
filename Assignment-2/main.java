import java.util.*;
import java.lang.*;

interface showing_available_deals{
    public void show_available_deals();

    public void explore_products_catalogue();
}

class Application implements showing_available_deals{
    // private admin a = new admin();
    private Vector<customer> registered_cutomers = new Vector<customer>();
    private Vector<deals> deals_available = new Vector<deals>();
    private Vector<categories> categories_available = new Vector<categories>();

    public Application(){
        
    }

    public Vector<categories> getCategories_available() {
        return categories_available;
    }

    public Vector<deals> getDeals_available() {
        return deals_available;
    }
    
    // public admin getA() {
    //     return a;
    // }
    
    public Vector<customer> getRegistered_cutomers() {
        return registered_cutomers;
    }
    
    public void add_registered_customer(customer c){
        boolean ans = false;
        for(int i=0;i<registered_cutomers.size();i++){
            if(registered_cutomers.get(i).getName().equals(c.getName()) && registered_cutomers.get(i).getpass().equals(c.getpass())){
                ans = true;
            }
        }

        if(ans == false){
            registered_cutomers.addElement(c);
            System.out.println("customer successfully registered!! \n");
        }
        else{
            System.out.println("Customer with given name and password has already registered !!");
        }
    }
    
    public void add_deals(deals d){
        this.deals_available.addElement(d);
    }
    public void add_category_available(categories c){
        this.categories_available.addElement(c);
    }

    public customer log_in(String name, String password){
        customer c1 = null;
        for(int i=0;i<registered_cutomers.size();i++){
            if(registered_cutomers.get(i).getName().equals(name) && registered_cutomers.get(i).getpass().equals(password)){
                c1 = registered_cutomers.get(i);
            }
        }

        return c1;
    }


    // functions
    // public boolean check_admin_details(String name, int roll, admin a){
    //     return a.get_admin_name().equals(name) && (a.get_admin_roll() == roll);
    // }
    @Override
    public void explore_products_catalogue(){
        if(this.categories_available.size() > 0){
            System.out.println("The products are: \n");

            for(int i=0;i<this.categories_available.size();i++){
                System.out.println("Category Name: " + this.categories_available.get(i).get_category_name() + "\n");
                Vector<product> p = this.categories_available.get(i).getCategory_p();
                for(int j=0;j<p.size();j++){
                    System.out.println(p.get(j).toString());

                    System.out.println("\n");
                }
            }
        }
        else{
            System.out.println("Dear User, there are no products for now!!! Please check regularly for exciting products.");
        }
    }

    @Override
    public void show_available_deals(){
        if(this.deals_available.size() > 0){
            for(int i=0;i<this.deals_available.size();i++){
                System.out.println(this.deals_available.get(i).toString());
                System.out.println("\n");
            }
        }
        else{
            System.out.println("Dear User, there are no deals for now!!! Please check regularly for exciting deals.");
        }
    }

    public boolean check_category(String id){
        for(int i=0;i<categories_available.size();i++){
            if(categories_available.get(i).get_category_id().equals(id)){
                return true;
            }
        }

        return false;
    }

    public boolean check_product_categorywise(String c_id, String p_id){
        for(int i=0;i<categories_available.size();i++){
            if(categories_available.get(i).get_category_id().equals(c_id)){
                Vector<product> p = categories_available.get(i).getCategory_p();
                for(int j=0;j<p.size();j++){
                    if(p.get(j).get_product_id().equals(p_id)){
                        return true;
                    }
                }
            }
        }

        return false;
    }
    
}

class admin extends Application{
    private String admin_name;
    private int admin_roll;

    Scanner sc = new Scanner(System.in);

    public admin(){
        this.admin_name = "Om Garg";
        this.admin_roll = 2021481;
    }

    public String get_admin_name(){
        return this.admin_name;
    }
    public int get_admin_roll(){
        return this.admin_roll;
    }

    public void add_category(String c_id, Application app){
        // check for exsisting category

        if(app.check_category(c_id) == false){
            System.out.println("Add name of category");
            String c = sc.nextLine();

            categories c1 = new categories(c, c_id);
            System.out.println(" \nAdd a Product: \n");
            // adding a product
            String p_name = "";
            String p_id = "";
            String p_description = "";
            double p_price = 0;
            int p_quantity = 0;

            while(true){
                String input = sc.nextLine();
                String[] arrOfStr = input.split(":");
                if(arrOfStr[0].equals("Quantity")){
                    p_quantity = Integer.parseInt(arrOfStr[1]);
                    break;
                }
                else if(arrOfStr[0].equals("Product Name")){
                    p_name = arrOfStr[1];
                }
                else if(arrOfStr[0].equals("Product ID")){
                    p_id = arrOfStr[1];
                }
                else if(arrOfStr[0].equals("Price")){
                    p_price = Double.parseDouble(arrOfStr[1]);
                }
                else{
                    p_description += input + "\n";
                }
            }

            product p1 = new product(p_name, p_id, p_description, p_price, p_quantity);

            c1.add_product(p1);

            app.add_category_available(c1);

        }
        else{
            System.out.println("Dear Admin, the category ID is already used!!! Please set a different and a unique category ID");
        }
    }

    public void delete_category(String name, String id, Application app){
        Vector<categories> c = app.getCategories_available();

        for(int i=0;i<c.size();i++){
            if(c.get(i).get_category_name().equals(name) && c.get(i).get_category_id().equals(id)){
                c.remove(i);
                break;
            }
        }
    }

    public void add_product(String c_id, String name, String p_id, String description, double price, int q, Application app){
        if(app.check_product_categorywise(c_id, p_id) == false){
            Vector<categories> c = app.getCategories_available();

            for(int i=0;i<c.size();i++){
                if(c.get(i).get_category_id().equals(c_id)){
                    product p = new product(name, p_id, description, price, q);
                    c.get(i).add_product(p);
                    break;
                }
            }   
        }
        else{
            System.out.println("Dear Admin, the Product ID is already used!!! Please set a different and a unique Product ID");
        }
    }

    public void delete_product(String c_name, String p_id, Application app){
        Vector<categories> c = app.getCategories_available();

        for(int i=0;i<c.size();i++){
            if(c.get(i).get_category_name().equals(c_name)){
                Vector<product> p = c.get(i).getCategory_p();
                for(int j=0;j<p.size();j++){
                    if(p.get(j).get_product_id().equals(p_id)){
                        p.remove(j);
                    }
                }

                if(p.size() == 0){
                    // ask for product addition
                    // Scanner sc = new Scanner(System.in);
                    String choice;
                    System.out.println("Want to add a product 1. Yes  2. NO");
                    choice = sc.nextLine();

                    if(choice.equals("Yes")){
                        String product_name = "";
                        String product_id = "";
                        String product_description = "";
                        double product_price = 0;
                        int product_q = 0;

                        while(true){
                            String input = sc.nextLine();
                            String[] arrOfStr = input.split(":");
                            if(arrOfStr[0].equals("Quantity")){
                                product_q = Integer.parseInt(arrOfStr[1]);
                                break;
                            }
                            else if(arrOfStr[0].equals("Product Name")){
                                product_name = arrOfStr[1];
                            }
                            else if(arrOfStr[0].equals("Product ID")){
                                product_id = arrOfStr[1];
                            }
                            else if(arrOfStr[0].equals("Price")){
                                product_price = Double.parseDouble(arrOfStr[1]);
                            }
                            else{
                                product_description += input + "\n";
                            }
                        }
                        product p1 = new product(product_name, product_id, product_description, product_price, product_q);

                        p.addElement(p1);
                    }
                    else{
                        System.out.println("Add Category ID");
                        String category_id = sc.nextLine();
                        System.out.println("Add name of category");
                        String category_name = sc.nextLine();

                        delete_category(category_name, category_id, app);
                    }
                }
            }
        }
    }

    public void set_discount_product(String p_id, double elite, double prime, double normal, Application app){
        Vector<categories> c = app.getCategories_available();

        for(int i=0;i<c.size();i++){
            Vector<product> p = c.get(i).getCategory_p();
            for(int j=0;j<p.size();j++){
                if(p.get(j).get_product_id().equals(p_id)){

                    p.get(j).set_discount(elite, prime, normal);
                    break;
                }
            }
        }
    }

    public void add_giveaway_deal(String id1, String id2, double price_e, double price_p, double price_n, String id, Application app){
        product p1 = null;
        product p2 = null;
        
        Vector<categories> c = app.getCategories_available();
        for(int i=0;i<c.size();i++){
            Vector<product> p = c.get(i).getCategory_p();
            for(int j=0;j<p.size();j++){
                if(p.get(j).get_product_id().equals(id1)){
                    p1 = p.get(j);
                }
                else if(p.get(j).get_product_id().equals(id2)){
                    p2 = p.get(j);
                }
            }
        }
        
        if(p1 != null && p2 != null){
        double sum = p1.get_product_price() + p2.get_product_price();
            if( sum > price_e && sum > price_p && sum > price_n ){
                deals d = new deals(id, p1, p2, price_e, price_p, price_n);

                boolean ans = false;
                Vector<deals> d_vec = app.getDeals_available();
                for(int i=0;i<d_vec.size();i++){
                    if(d_vec.get(i).getDeal_id().equals(id)){
                        ans = true;
                    }
                }

                if(ans == false){
                    app.add_deals(d);
                }
                else{
                    System.out.println("Deal ID already exsists");
                }

            }
            else{
                System.out.println("Effective Price is greater than Combined Price");
            }
        }
        else{
            System.out.println("Products ID may not be available");
        }

    }


}

class categories{
    private String category_id;
    private String category_name;

    private Vector<product> category_p = new Vector<product>();

    public categories(String c, String id){
        this.category_name = c;
        this.category_id = id;
    }

    public String get_category_name(){
        return this.category_name;
    }

    public String get_category_id(){
        return this.category_id;
    }

    public Vector<product> getCategory_p() {
        return category_p;
    }

    public void add_product(product p){
        this.category_p.addElement(p);
    }

}

class product{
    private String product_name;
    private String product_id;
    private String product_decription;
    private double product_price;
    private int quantity;

    private Vector<Double> discount_per_category = new Vector<Double>();

    public String get_product_id(){
        return this.product_id;
    }

    public double get_product_price(){
        return this.product_price;
    }

    public int getquantity() {
        return this.quantity;
    }

    public product(String name, String id, String description, double price, int q){
        this.product_name = name;
        this.product_id = id;
        this.product_decription = description;
        this.product_price = price;
        this.quantity = q;

        this.discount_per_category.addElement(0.0);
        this.discount_per_category.addElement(0.0);
        this.discount_per_category.addElement(0.0);
    }

    public void set_discount(double elite, double prime, double nrml){
        this.discount_per_category.set(0, elite);
        this.discount_per_category.set(1, prime);
        this.discount_per_category.set(2, nrml);
    }

    public void set_quantity(int q){
        this.quantity = q;
    }

    public double getDiscount_per_category(int i) {
        return discount_per_category.get(i);
    }

    public String toString(){
        return "Product Name " + this.product_name + "\n" + "Product ID " + this.product_id + "\n"+ this.product_decription + "Price :" + this.product_price;
    }

}

class deals{
    private String deal_id;
    private product product1;
    private product product2;
    
    private Vector<Double> effective_price = new Vector<Double>();

    public String getDeal_id() {
        return deal_id;
    }

    public double getEffective_price(int index) {
        return effective_price.get(index);
    }

    public deals(String id, product p1, product p2, double price_elite, double price_prime, double price_normal){
        this.product1 = p1;
        this.product2 = p2;
        this.deal_id = id;

        this.effective_price.addElement(price_elite);
        this.effective_price.addElement(price_prime);
        this.effective_price.addElement(price_normal);
    }

    public String toString(){
        return this.deal_id + "\n" + this.product1.toString() + "\n" + this.product2.toString() + "\n" + this.effective_price.get(0) + " " + this.effective_price.get(1) + " " + this.effective_price.get(2);
    }
}

class customer extends Application{
    private String name;
    private String email;
    private int age;
    private String phone;
    private String password;
    private double wallet;

    private Vector<product> cart_p = new Vector<product>();
    private Vector<Integer> quantity_p = new Vector<Integer>();
    private Vector<deals> cart_d = new Vector<deals>();
    private Vector<Integer> quantity_d = new Vector<Integer>();
    private Vector<Double> coupons = new Vector<Double>();
    private boolean is_registered;
    private type t = null;

    public customer(String name, String password){
        this.name = name;
        this.email = "helloworld@123";
        this.age = 18;
        this.phone = "1234567890";
        this.password = password;
        this.wallet = 1000;

        this.is_registered = false;
        this.t = new normal();
    }

    public String getName() {
        return name;
    }

    public String getpass() {
        return password;
    }

    public void upgrade_status(String s){
        if(s.equals("ELITE")){
            this.t = new elite();
            make_payment(300);
        }
        else if(s.equals("PRIME")){
            this.t = new prime();
            make_payment(200);
        }

        System.out.println("Status Updated to " + t.getName());
    }

    public void add_wallet(double amount){
        this.wallet += amount;
        System.out.println("Amount succesfully added");
    }

    public void explore_products(Application app){
        app.explore_products_catalogue();
    }

    public void browse_deals(Application app){
        app.show_available_deals();
    }

    public void add_product_cart(String p_id, int quantity, Application app){
        Vector<categories> c = app.getCategories_available();

        product new_p = null;
        for(int i=0;i<c.size();i++){
            Vector<product> p = c.get(i).getCategory_p();
            for(int j=0;j<p.size();j++){
                if(p.get(j).get_product_id().equals(p_id)){
                    new_p = p.get(j);
                }
            }
        }

        if(new_p != null){
            // checking quantity
            if(new_p.getquantity() - quantity < 0){
                if(new_p.getquantity() == 0){
                    System.out.println("Product Out of Stock");
                }
                else{
                    System.out.println("Requested amount not available!!");
                }
                return;
            }

            boolean status = false;

            for(int i=0;i<this.cart_p.size();i++){
                if(this.cart_p.get(i).get_product_id().equals(new_p.get_product_id())){
                    int q = this.quantity_p.get(i) + quantity;
                    this.quantity_p.set(i, q);
                    status = true;
                }
            }

            if(status == false){
                this.cart_p.addElement(new_p);
                this.quantity_p.addElement(quantity);
            }

            System.out.println("Item successfully added to cart");
        }
        else{
            System.out.println("Product could not be found with given ID");
        }
    }

    public void add_deal_cart(String d_id, Application app){
        Vector<deals> d = app.getDeals_available();
        deals new_d = null;
        for(int i=0;i<d.size();i++){
            if(d.get(i).getDeal_id().equals(d_id)){
                new_d = d.get(i);
            }
        }

        if(new_d != null){
            boolean status = false;

            for(int i=0;i<this.cart_d.size();i++){
                if(this.cart_d.get(i).getDeal_id().equals(new_d.getDeal_id())){
                    int q = this.quantity_d.get(i) + 1;
                    this.quantity_d.set(i, q);
                    status = true;
                }
            }

            if(status == false){
                this.cart_d.addElement(new_d);
                this.quantity_d.addElement(1);
            }
            System.out.println("Item successfully added to cart");
        }
        else{
            System.out.println("Deal cannot be found with given ID");
        }
    }

    public void get_coupons(){
        System.out.println("Available coupons are: ");
        for(int i=0;i<this.coupons.size();i++){
            System.out.println(this.coupons.get(i));
        }
    }

    public void check_account_balance(){
        System.out.println("Current acount balace is Rupees " + this.wallet);
    }

    public void view_cart(){
        for(int i=0;i<this.cart_p.size();i++){
            System.out.println(this.cart_p.get(i).toString());
            System.out.println("Quantity :" + this.quantity_p.get(i));
            System.out.println("\n");
        }
        
        for(int i=0;i<this.cart_d.size();i++){
            System.out.println(this.cart_d.get(i).toString());
            System.out.println("Quantity: " + this.quantity_d.get(i));
            System.out.println("\n");
        }
    }

    public void empty_cart(){
        this.cart_d.clear();
        this.quantity_d.clear();

        this.cart_p.clear();
        this.quantity_p.clear();
        
    }

    
    public boolean make_payment(double bill){
        if(bill <= this.wallet){
            this.wallet -= bill;
            return true;
        }
        else{
            System.out.println("Insufficient balance!! Please try again");
        }

        return false;
    }
    
    public void checkout_cart(){
        double value_of_cart = 0;
        double value_of_cart_original = 0;

        // calculating discounts
        int index = -1;
        if(t.getName().equals("NORMAL")){
            index = 2;
        }
        else if(t.getName().equals("PRIME")){
            index = 1;
        }
        else if(t.getName().equals("ELITE")){
            index = 0;
        }

        
        // apply discounts
        double mem_discount = this.t.getMembership_discount();
        
        // applying coupons
        double max_discount = 0;
        int c_in = -1;
        
        for(int i=0;i<this.coupons.size();i++){
            if(max_discount < this.coupons.get(i)){
                c_in = i;
                max_discount = this.coupons.get(i);
            }
        }
        
        double c_discount = max_discount;

        double c_vs_mem_discount = 0.0;
        if(mem_discount > c_discount){
            c_vs_mem_discount = mem_discount;
        }
        else{
            c_vs_mem_discount = c_discount;
        }
        
        // deals cart
        for(int i=0;i<this.cart_d.size();i++){
            value_of_cart += cart_d.get(i).getEffective_price(index)*quantity_d.get(i);
            value_of_cart_original += cart_d.get(i).getEffective_price(index)*quantity_d.get(i);
        }

        for(int i=0;i<this.cart_p.size();i++){
            double price = cart_p.get(i).get_product_price();

            double p_discount = cart_p.get(i).getDiscount_per_category(index);
            double discount = 0.0;
            if(p_discount > c_vs_mem_discount){
                discount = p_discount;
            }
            else{
                discount = c_vs_mem_discount;
            }

            value_of_cart += (price - price*(discount/100))*quantity_p.get(i);
            value_of_cart_original += (price)*quantity_p.get(i);
        }
        
        // if user is elegible for coupons
        boolean eligible_for_coupons = false;
        if(value_of_cart_original > 5000  && t.getName() != "NORMAL"){
            eligible_for_coupons = true;
        }
        
        // value_of_cart = value_of_cart - mem_discount - c_discount + delivery_charges;
        
        // double total_discount = mem_discount + c_discount;
        
        double delivery_charges = 100 + value_of_cart_original*(t.getDelivery_charge()/100);
        value_of_cart += delivery_charges;

        // make payment
        boolean status = make_payment(value_of_cart);
        if(status == true){
            System.out.println("Your order is placed succesfully");
            // wallet balance reduction already done  and making cart empty
            System.out.println();
            // showing product
            this.view_cart();

            // quantity subtracting
            for(int i=0;i<cart_p.size();i++){
                int q = cart_p.get(i).getquantity() - quantity_p.get(i);
                cart_p.get(i).set_quantity(q);
            }

            // emptying cart
            this.empty_cart();

            // removing coupon at end
            if(c_in != -1){
                this.coupons.remove(c_in);
            }

            // making bill
            System.out.println("Delivery charges: " + delivery_charges);
            // System.out.println("Total discount: " +  " = " + total_discount);

            System.out.println("Total cost: " + value_of_cart);

            // setting delivery days and number of coupons
            int number_c = 0;
            if(t.getName().equals("NORMAL")){
                System.out.println("\nYour order will be delivered within 7-10 days");
                number_c = 0;
            }
            else if(t.getName().equals("PRIME")){
                System.out.println("\n Your order will be delivered within 3-6 days");
                number_c =  (int)(Math.random()*2) + 1;
            }
            else{
                System.out.println("\n Your order will be delivered within 2 days");
                number_c =  (int)(Math.random()*2) + 3;
            }
            

            
            // adding coupons if eligible
            if(eligible_for_coupons){
                System.out.print("You have won " + number_c + " coupons of ");
                for(int i=0;i<number_c;i++){
                    double value = (double)((int)(Math.random()*11) + 5);
                    this.coupons.addElement(value);
                    System.out.print(value + "% ");
                }
                System.out.println(" discount Congratulations!!");
                System.out.println(this.coupons.size());
            }

            // adding surprise for elite members
            if(t.getName().equals("ELITE")){
                System.out.println("\nCongratulations!! you have won a surprise");
            }

            System.out.println();

        }

    }
}

class type{
    private String name;
    private double membership_discount;
    private int membership_duration;
    // private int number_coupons_order;
    private double delivery_charge;
    private int delivery_days;
    

    public type(String n, double discount, double charge){
        this.name = n;
        this.membership_discount = discount;
        this.membership_duration = 30;
        this.delivery_charge = charge;
    }

    // public void set_delivery_days(int days){
    //     this.delivery_days = days;
    // }

    // public void set_coupons(int c){
    //     this.number_coupons_order = c;
    // }

    public String getName() {
        return this.name;
    }

    public double getMembership_discount() {
        return membership_discount;
    }

    // public int getNumber_coupons_order() {
    //     return number_coupons_order;
    // }

    public double getDelivery_charge() {
        return delivery_charge;
    }

    public int getDelivery_days() {
        return delivery_days;
    }
}

class normal extends type{
    public normal(){
        super("NORMAL", 0, 5);
    }

    // @Override
    // public void set_delivery_days(int days) {
    //     super.set_delivery_days(days);
    // }

    // @Override
    // public void set_coupons(int c) {
    //     super.set_coupons(c);
    // }
}

class prime extends type{
    public prime(){
        super("PRIME", 5, 2);
    }

    // @Override
    // public void set_delivery_days(int days) {
    //     super.set_delivery_days(days);
    // }

    // @Override
    // public void set_coupons(int c) {
    //     super.set_coupons(c);
    // }
}

class elite extends type{
    public elite(){
        super("ELITE", 10, 0);
    }

    // @Override
    // public void set_delivery_days(int days) {
    //     super.set_delivery_days(days);
    // }

    // @Override
    // public void set_coupons(int c) {
    //     super.set_coupons(c);
    // }
}



public class assignment2_2021481 {
    public static void main(String[] args) {
        System.out.println("WELCOME TO FLIPZON \n");
        Application app = new Application();
        admin a = new admin();
        Scanner sc1 = new Scanner(System.in);   // for input of integers
        Scanner sc2 = new Scanner(System.in);   // for input of strings

        int choice;

        while(true){
            System.out.println(" 1) Enter as Admin \n 2) Explore the Product Catalog \n 3) Show Available Deals \n 4) Enter as Customer \n 5) Exit the Application \n");
            choice = sc1.nextInt();

            if(choice == 1){
                System.out.println("Dear Admin, \n Please enter your username and password \n");
                String name = sc2.nextLine();
                int id = sc1.nextInt();

                System.out.println();

                if(a.get_admin_name().equals(name) && (a.get_admin_roll() == id)){
                    // admin a = app.getA();
                    System.out.println("Welcome " + name);
                    while(true){
                        System.out.println(" Please choose any one of the following actions:");
                        System.out.println(" 1) Add category \n 2) Delete category \n 3) Add Product \n 4) Delete Product \n 5) Set Discount on Product \n 6) Add giveaway deal \n 7) Back \n");

                        choice = sc1.nextInt();

                        if(choice == 1){
                            System.out.println("Add Category ID");
                            String category_id = sc2.nextLine();

                            a.add_category(category_id, app);

                            
                        }
                        else if(choice == 2){
                            System.out.println("Enter a Category Name: ");
                            String c_name = sc2.nextLine();
                            System.out.println("Enter a Category ID: ");
                            String c_id = sc2.nextLine();

                            a.delete_category(c_name, c_id, app);
                        }
                        else if(choice == 3){
                            System.out.println("Enter a category ID");
                            String c_id = sc2.nextLine();
                            System.out.println("Add a Product \n");

                            String p_name = "";
                            String p_id = "";
                            String p_description = "";
                            double p_price = 0;
                            int p_quantity = 0;

                            while(true){
                                String input = sc2.nextLine();
                                String[] arrOfStr = input.split(":");
                                if(arrOfStr[0].equals("Quantity")){
                                    p_quantity = Integer.parseInt(arrOfStr[1]);
                                    break;
                                }
                                else if(arrOfStr[0].equals("Product Name")){
                                    p_name = arrOfStr[1];
                                }
                                else if(arrOfStr[0].equals("Product ID")){
                                    p_id = arrOfStr[1];
                                }
                                else if(arrOfStr[0].equals("Price")){
                                    p_price = Double.parseDouble(arrOfStr[1]);
                                }
                                else{
                                    p_description += input + "\n";
                                }
                            }

                            a.add_product(c_id, p_name, p_id, p_description, p_price, p_quantity, app);
                        }
                        else if(choice == 4){
                            System.out.println("Enter a Category Name:");
                            String category_name = sc2.nextLine();
                            System.out.println("Enter a Product ID");
                            String p_id = sc2.nextLine();

                            a.delete_product(category_name, p_id, app);
                        }
                        else if(choice == 5){
                            System.out.println("Enter a product ID");
                            String p_id = sc2.nextLine();
                            System.out.println("Enter discount for Elite, Prime and Normal customers respectively (in % terms)");

                            double elite = sc1.nextInt();
                            double prime = sc1.nextInt();
                            double normal = sc1.nextInt();

                            a.set_discount_product(p_id, elite, prime, normal, app);
                        }
                        else if(choice == 6){
                            System.out.println("Dear Admin give the Product IDs you want to combine and giveaway a deal for \n");
                            System.out.println("Enter the first Product ID :");
                            String p1_id = sc2.nextLine();
                            System.out.println("Enter the second Product ID:");
                            String p2_id = sc2.nextLine();

                            System.out.println("Enter the combined price(Should be less than their combined price)[Elite]:");
                            double d_price_E = sc1.nextDouble();
                            System.out.println("Enter the combined price(Should be less than their combined price)[Prime]:");
                            double d_price_P = sc1.nextDouble();
                            System.out.println("Enter the combined price(Should be less than their combined price)[Normal]:");
                            double d_price_N = sc1.nextDouble();
                            

                            
                            System.out.println("Please enter the deal ID");
                            String d_id = sc2.nextLine();

                            a.add_giveaway_deal(p1_id, p2_id, d_price_E, d_price_P, d_price_N, d_id, app);
                        }
                        else{
                            break;
                        }
                        System.out.println();
                    }

                }
                else{
                    System.out.println("Wrong Details Please try Again");
                }
                System.out.println();
                
            }
            else if(choice == 2){
                app.explore_products_catalogue();
            }
            else if(choice == 3){
                app.show_available_deals();
            }
            else if(choice == 4){
                while(true){
                    System.out.println(" 1) Sign up \n 2) Log in \n 3) Back");

                    choice = sc1.nextInt();

                    if(choice == 1){
                        System.out.println("Enter name");
                        String name = sc2.nextLine();
                        System.out.println("Enter password");
                        String password = sc2.nextLine();

                        customer c1 = new customer(name, password);

                        app.add_registered_customer(c1);
                    }
                    else if(choice == 2){
                        System.out.println("Enter name");
                        String name = sc2.nextLine();
                        System.out.println("Enter password");
                        String password = sc2.nextLine();

                        customer c1 = app.log_in(name, password);
                    
                        if(c1 != null){
                            System.out.println("\nWelcome " + name);

                            while(true){
                                System.out.println(" 1) browse products \n 2) browse deals \n 3) add a product to cart \n 4) add products in deal to cart \n 5) view coupons \n 6) check account balance \n 7) view cart \n 8) empty cart \n 9) checkout cart \n 10) upgrade customer status \n 11) Add amount to wallet \n 12) back \n");

                                choice = sc1.nextInt();

                                if(choice == 1){
                                    c1.explore_products(app);
                                }
                                else if(choice == 2){
                                    c1.browse_deals(app);
                                }
                                else if(choice == 3){
                                    System.out.println("Enter product ID and quantity");
                                    String p_id = sc2.nextLine();
                                    int q = sc1.nextInt();

                                    c1.add_product_cart(p_id, q, app);

                                }
                                else if(choice == 4){
                                    System.out.println("Enter Deal ID");
                                    String d_id = sc2.nextLine();

                                    c1.add_deal_cart(d_id, app);
                                }
                                else if(choice == 5){
                                    c1.get_coupons();
                                }
                                else if(choice == 6){
                                    c1.check_account_balance();
                                }
                                else if(choice == 7){
                                    c1.view_cart();
                                }
                                else if(choice == 8){
                                    c1.empty_cart();
                                    System.out.println("Cart succesfully emptied");
                                }
                                else if(choice == 9){
                                    c1.checkout_cart();
                                }
                                else if(choice == 10){
                                    System.out.print("Choose new status: ");
                                    String status = sc2.nextLine();

                                    c1.upgrade_status(status);
                                }
                                else if(choice == 11){
                                    System.out.println("Enter amount to add");
                                    double amount = sc1.nextDouble();

                                    c1.add_wallet(amount);
                                }
                                else if(choice == 12){
                                    System.out.println("Bye " + name);
                                    break;
                                }
                                else{
                                    break;
                                }
                                System.out.println();
                            }
                        }
                        else{
                            System.out.println("Invalid Credentials");
                        }
                        System.out.println();
                    }
                    else{
                        break;
                    }
                    System.out.println();
                }
            }
            else{
                break;
            }
            System.out.println();
        }
    }
}
