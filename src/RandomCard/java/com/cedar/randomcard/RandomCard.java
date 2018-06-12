package com.cedar.randomcard;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;


public class RandomCard {
    private static final Logger logger = LogManager.getLogger("RandomCard");

    public static void main(String[] args) {
        long startn, totaln;
        if (2 == args.length) {
            startn = Long.parseUnsignedLong(args[0]);
            totaln = Long.parseUnsignedLong(args[1]);
        } else if (args.length == 1 ) {
            startn= 450000000000000L; // default card start.
            totaln = Long.parseUnsignedLong(args[0]);
        } else {
            startn = 450000000000000L;
            totaln = 1000;
        }
        logger.info( "Card# starts at:" + startn + " for total: " + totaln + " of cards!");
        logger.info("Card Gen Start: Start at:" + startn + " total: " + totaln);
        List<Long> cards = LongStream.range(startn, startn+totaln)
                .map(Mod10Utils::appendMod10)
                .boxed().collect(Collectors.toList());
        logger.info(cards.size() + " of cards generated!");

        logger.info("start shuffle");
        Collections.shuffle(cards);
        logger.info("End shuffle");

        logger.info("Start Writing to file");
        try(PrintWriter pw = new PrintWriter(Files.newBufferedWriter(Paths.get("numbers.txt")))) {
            cards.forEach(pw::println);
        } catch (IOException e) {
            logger.error(e);
        }
        logger.info("Complete writing to file ");
    }
}


