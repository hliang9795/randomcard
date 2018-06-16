package com.cedar.number.fun;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.LongStream;


public class RandomCard {
    private static final Logger logger = LogManager.getLogger("RandomCard");

    public static void main(String[] args) {
        long startn;
        int size;
        if (2 == args.length) {
            startn = Long.parseUnsignedLong(args[0]);
            size = Integer.parseUnsignedInt(args[1]);
        } else if (args.length == 1 ) {
            startn= 450000000000000L; // default card start.
            size = Integer.parseUnsignedInt(args[0]);
        } else {
            startn = 450000000000000L;
            size = 1000;
        }
        logger.info( "Card# starts at:" + startn + " for total: " + size + " of cards!");


        RandomCard rc = new RandomCard();
        rc.randomCardGen_M1(startn, size, "Numbers_M1.txt");
        rc.randomCardGen_M2(startn, size, "Numbers_M2.txt");
        rc.randomCardGen_M3(startn, size, "Numbers_M3.txt");
    }

    public void randomCardGen_M1(long origin, int size, String fn) {
        logger.info("enter randomCardGen_M1");
        logger.info("Begin Card# Gen");
        List<Long> cards = LongStream.range(origin, origin+size)
                .map(Mod10Utils::appendMod10)
                .boxed().collect(Collectors.toList());
        logger.info(cards.size() + " of cards generated!");
        logger.info("End Card# Gen");

        logger.info("Begin shuffle");
        Collections.shuffle(cards);
        logger.info("End shuffle");

        logger.info("Begin Writing to file");
        try(PrintWriter pw = new PrintWriter(Files.newBufferedWriter(Paths.get(fn), Charset.defaultCharset())) ) {
            cards.forEach(pw::println);
        } catch (IOException e) {
            logger.error(e);
        }
        logger.info("End Writing to file");
        logger.info("Leave randomCardGen_M1");
    }

    public void randomCardGen_M2(long origin, int size, String fn) {
        logger.info("enter randomCardGen_M2");
        try(PrintWriter pw = new PrintWriter(Files.newBufferedWriter(Paths.get(fn)))) {
            Random rn= new Random();
            rn.longs(origin, origin+size).distinct().map(Mod10Utils::appendMod10).limit(size).forEach(pw::println);
        } catch (IOException e) {
            logger.error(e);
        }
        logger.info("leave randomCardGen_M2");
    }

    public void randomCardGen_M3(long origin, int size, String fn) {
        logger.info("enter randomCardGen_M3");
        try(PrintWriter pw = new PrintWriter(Files.newBufferedWriter(Paths.get(fn)))) {
            long c[] = new long[size];
            for (int i=0; i<size; i++) c[i]=origin+i;
            Random r = new Random();
            for (int j=size-1; j>0; j--) {
                int k = r.nextInt(j);
                long t = c[j];
                c[j] = c[k];
                c[k] = t;
                c[j]=Mod10Utils.appendMod10(c[j]);
                pw.println(c[j]);
            }
            c[0]=Mod10Utils.appendMod10(c[0]);
            pw.println(c[0]);
        } catch (IOException e) {
            logger.error(e);
        }
        logger.info("leave randomCardGen_M3");
    }

    public void randomCardGen_M4(long origin, int size, String fn) {
        logger.info("enter randomCardGen_M4");
        try(PrintWriter pw = new PrintWriter(Files.newBufferedWriter(Paths.get(fn)))) {
            int c[] = new int[size];
            for (int i=0; i<size; i++) c[i]=i;
            Random r = new Random();
            for (int j=size-1; j>0; j--) {
                int k = r.nextInt(j);
                int t = c[j];
                c[j] = c[k];
                c[k] = t;
                pw.println(Mod10Utils.appendMod10(c[j]+origin));
            }
            pw.println(Mod10Utils.appendMod10(c[0]+ origin));
        } catch (IOException e) {
            logger.error(e);
        }
        logger.info("leave randomCardGen_M4");
    }
}



