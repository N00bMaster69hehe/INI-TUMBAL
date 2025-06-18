package com.mycompany.mavenproject3;

import javax.swing.*;
import java.awt.*;

public class Mavenproject3 extends JFrame implements Runnable {
    private String text;
    private int x;
    private int width;
    private BannerPanel bannerPanel;
    private JButton addProductButton;
    private JButton sellingButton;
    private JButton customerButton;
    private JButton loginButton;
    private JPanel bottomPanel;

    public Mavenproject3() {
        ProductForm form = new ProductForm();
        this.text = form.getProductBannerText();

        setTitle("WK. STI Chill");
        setSize(600, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel teks berjalan (banner)
        bannerPanel = new BannerPanel();
        add(bannerPanel, BorderLayout.CENTER);

        // Panel bawah untuk tombol
        bottomPanel = new JPanel();
        add(bottomPanel, BorderLayout.SOUTH);

        // Tombol Login
        loginButton = new JButton("Login");
        bottomPanel.add(loginButton);

        // Tombol-tombol utama (disiapkan tapi belum ditampilkan)
        addProductButton = new JButton("Kelola Produk");
        customerButton = new JButton("Kelola Customer");
        sellingButton = new JButton("Penjualan");

        // Aksi tombol login
        loginButton.addActionListener(e -> {
            // Simulasikan login berhasil, lalu tampilkan tombol utama
            showMainButtons();
            bottomPanel.revalidate();
            bottomPanel.repaint();
        });

        // Tambahkan listener pembaruan teks banner
        form.setProductChangeListener(() -> {
            SwingUtilities.invokeLater(() -> {
                updateBannerText(form.getProductBannerText());
            });
        });

        // Aksi tombol lainnya
        addProductButton.addActionListener(e -> {
            form.setVisible(true);
            updateBannerText(form.getProductBannerText());
        });

        customerButton.addActionListener(e -> {
            CustomerForm customerForm = new CustomerForm();
            customerForm.setVisible(true);
        });

        sellingButton.addActionListener(e -> {
            SellingForm sellingForm = new SellingForm(form);
            sellingForm.setVisible(true);
        });

        setVisible(true);

        Thread thread = new Thread(this);
        thread.start();
    }

    private void showMainButtons() {
        // Hapus tombol login
        bottomPanel.removeAll();
        // Tambahkan tombol-tombol utama
        bottomPanel.add(addProductButton);
        bottomPanel.add(customerButton);
        bottomPanel.add(sellingButton);
    }

    class BannerPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 18));
            g.drawString(text, x, getHeight() / 2);
        }
    }

    @Override
    public void run() {
        width = getWidth();
        x = width;
        while (true) {
            x -= 5;
            if (x + bannerPanel.getFontMetrics(new Font("Arial", Font.BOLD, 18)).stringWidth(text) < 0) {
                x = width;
            }
            bannerPanel.repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void updateBannerText(String newText) {
        this.text = newText;
        x = -bannerPanel.getFontMetrics(new Font("Arial", Font.BOLD, 18)).stringWidth(newText);
    }

    public static void main(String[] args) {
        new Mavenproject3();
    }
}
