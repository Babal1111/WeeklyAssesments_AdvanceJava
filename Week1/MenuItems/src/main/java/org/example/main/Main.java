package org.example.main;

import org.example.entity.MenuItem;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {

            System.out.println(" Restaurant Menuu");
            System.out.println("1. Add Menu Item");
            System.out.println("2. View All Items");
            System.out.println("3. Update Price");
            System.out.println("4. Delete Item");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    addItem();
                    break;

                case 2:
                    viewItems();
                    break;

                case 3:
                    updatePrice();
                    break;

                case 4:
                    deleteItem();
                    break;

                case 5:
                    HibernateUtil.close();
                    System.out.println("Thank You!");
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }

    static void addItem() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        System.out.print("Enter Name: ");
        String name = sc.next();

        System.out.print("Enter Price: ");
        double price = sc.nextDouble();

        System.out.print("Enter Category: ");
        String category = sc.next();

        System.out.print("Available (true/false): ");
        boolean available = sc.nextBoolean();

        MenuItem item = new MenuItem(name, price, category, available);

        session.save(item);

        tx.commit();
        session.close();

        System.out.println("Item Added Successfully!");
    }

    static void viewItems() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Query<MenuItem> query =
                session.createQuery("from MenuItem", MenuItem.class);

        List<MenuItem> list = query.list();

        System.out.println("\n--- Menu Items ---");

        for (MenuItem m : list) {
            System.out.println(
                    m.getId() + " | " +
                            m.getName() + " | " +
                            m.getPrice() + " | " +
                            m.getCategory() + " | " +
                            m.isAvailable()
            );
        }

        session.close();
    }

    static void updatePrice() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        System.out.print("Enter ID: ");
        int id = sc.nextInt();

        MenuItem item = session.get(MenuItem.class, id);

        if (item != null) {

            System.out.print("Enter New Price: ");
            double price = sc.nextDouble();

            item.setPrice(price);

            session.update(item);

            tx.commit();
            System.out.println("Price Updated!");

        } else {
            System.out.println("Item Not Found!");
        }

        session.close();
    }


    static void deleteItem() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        System.out.print("Enter ID: ");
        int id = sc.nextInt();

        MenuItem item = session.get(MenuItem.class, id);

        if (item != null) {

            session.delete(item);
            tx.commit();

            System.out.println("Item Deleted!");

        } else {
            System.out.println("Item Not Found!");
        }

        session.close();
    }
}
