package com.minhle.tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ArticleExtractorUI extends JFrame {

    private JTextField urlField;
    private JButton fetchButton;
    private JEditorPane articlePane;

    public ArticleExtractorUI() {
        setTitle("Article Extractor");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        urlField = new JTextField();
        fetchButton = new JButton("Fetch Article");
        articlePane = new JEditorPane();
        articlePane.setEditable(false);
        articlePane.setContentType("text/html");

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(urlField, BorderLayout.NORTH);
        panel.add(fetchButton, BorderLayout.WEST);

        fetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchArticle();
            }
        });

        JScrollPane scrollPane = new JScrollPane(articlePane);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
    }

    private void fetchArticle() {
        String url = urlField.getText();
        if (url == null || url.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid URL");
            return;
        }

        try {
            // String html = ArticleExtractor.extractArticle(url);
            String html = ArticleExtractor.extractArticleText(url);
            articlePane.setText(html);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to fetch article: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ArticleExtractorUI().setVisible(true);
            }
        });
    }
}