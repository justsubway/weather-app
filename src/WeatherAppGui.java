import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class WeatherAppGui extends JFrame {
    private JSONObject weatherData;
    public WeatherAppGui(){

        super("Weather App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(450, 650);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);


        addGuiComponents();
    }

    private void addGuiComponents(){
        // search field
        JTextField searchField = new JTextField();
        searchField.setBounds(90, 15, 200, 45);
        searchField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(searchField);

        //weather condition image
        JLabel weatherCondImage = new JLabel(loadImage("src/assets/cloudy.png"));
        weatherCondImage.setBounds(0, 125, 450, 217);
        add(weatherCondImage);

        //temperature
        JLabel tempText = new JLabel("10 C");
        tempText.setBounds(0, 350, 450, 54);
        tempText.setFont(new Font("Dialog", Font.BOLD, 48));
        tempText.setHorizontalAlignment(SwingConstants.CENTER);
        add(tempText);

        //description
        JLabel condDescr = new JLabel("Cloudy");
        condDescr.setBounds(0, 405, 450, 36);
        condDescr.setFont(new Font("Dialog", Font.PLAIN, 32));
        condDescr.setHorizontalAlignment(SwingConstants.CENTER);
        add(condDescr);

        //HUMIDITY

        //image
        JLabel humimg = new JLabel(loadImage("src/assets/humidity.png"));
        humimg.setBounds(15, 500, 74, 66);
        add(humimg);
        //text
        JLabel humtext = new JLabel("<html><b>Humidity</b> 100%</html>");
        humtext.setBounds(90, 500, 85, 55);
        humtext.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(humtext);


        //WIND SPEED

        //image
        JLabel windspeedimg = new JLabel(loadImage("src/assets/windspeed.png"));
        windspeedimg.setBounds(220, 500, 74, 66);
        add(windspeedimg);

        //text
        JLabel windspeedtext = new JLabel("<html><b>Windspeed</b> 15km/h</html>");
        windspeedtext.setBounds(310, 500, 85, 55);
        windspeedtext.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(windspeedtext);

        //search button
        JButton searchButton = new JButton(loadImage("src/assets/search.png"));
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(300, 15, 47, 45);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = searchField.getText();

                if(userInput.replaceAll("\\s", "").length() <= 0){
                    return;
                }

                weatherData = WeatherApp.getWeatherData(userInput);

                String weatherCondition = (String) weatherData.get("weather_condition");

                switch(weatherCondition){
                    case "Clear":
                        weatherCondImage.setIcon(loadImage("src/assets/clear.png"));
                        break;
                    case "Cloudy":
                        weatherCondImage.setIcon(loadImage("src/assets/cloudy.png"));
                        break;
                    case "Rain":
                        weatherCondImage.setIcon(loadImage("src/assets/rain.png"));
                        break;
                    case "Snow":
                        weatherCondImage.setIcon(loadImage("src/assets/snow.png"));
                        break;
                }

                double temperature = (double) weatherData.get("temperature");
                tempText.setText((temperature + " C"));

                condDescr.setText(weatherCondition);

                long humidity = (long) weatherData.get("humidity");
                humtext.setText("<html><b>Humidity</b> " + humidity + "%</html>");

                double windspeed = (double) weatherData.get("windspeed");
                windspeedtext.setText("<html><b>Windspeed</b> " + windspeed + "km/h</html>");

            }
        });
        add(searchButton);

    }

    private ImageIcon loadImage(String resourcePath){
        try {
            BufferedImage image = ImageIO.read(new File(resourcePath));

            return new ImageIcon(image);
        }catch(IOException e){
            e.printStackTrace();
        }

        System.out.println("Image could not be found!");
        return null;
    }
}
