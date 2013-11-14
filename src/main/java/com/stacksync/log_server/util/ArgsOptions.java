package com.stacksync.log_server.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class ArgsOptions {
	
	private static final Logger logger = Logger.getLogger(ArgsOptions.class.getName());
	
	@SuppressWarnings("static-access")
	public static void readAndApplyOptions(String[] args) {
		
		Options options = new Options();
		options.addOption("h", "help", false, "print this message");
		options.addOption("c", "config", true, "path to the configuration file");
		options.addOption("V", "version", false, "print application version");
		Option dump = OptionBuilder.withLongOpt("dump-config").hasArg(false)
				.withDescription("dumps an example of configuration file, you can redirect the output to a new file to edit the configuration").create();
		options.addOption(dump);

		// create the parser
		CommandLineParser parser = new GnuParser();
		try {
			// parse the command line arguments
			CommandLine line = parser.parse(options, args);

			if (line.hasOption("help")) {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("Log-server [OPTION]...",
						"Runs the StackSync log-server with the options specified in the config file.\nAvailable options:", options, "");
				System.exit(0);
			}

			if (line.hasOption("version")) {
				String version = getVersion();
				System.out.println(String.format("StackSync log-server v%s", version));
				System.exit(0);
			}

			if (line.hasOption("dump-config")) {
				InputStream exampleStream = Config.class.getResourceAsStream("/example.properties");

				StringWriter writer = new StringWriter();
				IOUtils.copy(exampleStream, writer);
				String output = writer.toString();
				exampleStream.close();

				System.out.print(output);
				System.exit(0);
			}

			if (line.hasOption("config")) {
				String configFileUrl = line.getOptionValue("config");

				File file = new File(configFileUrl);
				if (!file.exists()) {
					System.err.println("ERROR: '" + configFileUrl + "' file not found");
					System.exit(2);
				}

				Config.loadProperties(configFileUrl);

			} else {
				Config.loadProperties();
			}

		} catch (ParseException exp) {
			// oops, something went wrong
			logger.error("Parsing failed. Reason: " + exp.getMessage());
			System.exit(1);
		} catch (IOException e) {
			logger.error("IOException. Reason: " + e.getMessage());
			System.exit(7);
		}
	}
	
	private static String getVersion() {
		String path = "/version.properties";
		InputStream stream = Config.class.getResourceAsStream(path);
		if (stream == null) {
			return "UNKNOWN";
		}
		Properties props = new Properties();
		try {
			props.load(stream);
			stream.close();
			return (String) props.get("version");
		} catch (IOException e) {
			return "UNKNOWN";
		}
	}

}
