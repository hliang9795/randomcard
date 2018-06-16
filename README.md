# Fun with Numbers

Manipulating numbers is the most important computer programming skills. Choosing the algorithm is 
both sceience and art of compromise. Wandering in this realm is really fun. 

Below are two problem I am playing with. :-)

# Random Card Generator

## Backgroud
The quest started from when I over heard that business guy would like to issue bank card with 
randomized card numbers so the fraudster will not able guess the card numbers issued recently. 

The card# of debit or credit card consist 3 parts: 
* a six-digit Issuer Identification Number (IIN), the first digit of which is the major 
industry identifier (MII). For example, '4' for banks. 
* a variable length (up to 12 digits) individual account identifier
* a single check digit calculated using the Luhn algorithm. This digits are commonly know as 
MOD10 checksum digits. 

In bank industry, the first 6-8 digits makes a BIN number, which represent the card product 
issued by particular bank.  

For example, card# 450601 2345678901 2, first 6 digits are BIN number, the middle 9 digits 
are account #, and the last digit is MOD10 checksum digits. 

Look above example, what business really want is to randomize the middle 9
digits number. 9 digits give 1 billion numbers, enough to give each Canadian 25 cards. 

Business does not need that much number. They normally assign a smaller range of card number 
belongs to same BIN for a product. For example, bank X' classic visa card number starts at
4500 0010 0000 000# to 4500 0100 9999 999#. Where the '#' is the MOD10 checksum digits. This
range of card has 10 million valid card. 

## First Sprint. 

### High Level Design
After analye the rquirement, the first intuitive solution is

* generate the 10 million card # sequencally. 
* randomize the 10 million numbers. 
* write the numbers to a file, one in a line. The file serve as un-used card # pool. 
* if business need N cards, we take N read N lines from the fime. 

The most interesting part at this spring is first 3 items. 

### Initial Try
Now the developer start programming with Java 1.6, which is the JDK version comes 
with weblogic server. Without modern Java language features, he has to sweat every details.

It is easy to generate the 9 digits random account number. The problem is that random number
genarator may duplicated with previous generated. If duplicate happens, the duplicated number
must discards. This requires
* store the already generated uniq card# to an array. 
* check if new generated number already in the array, if yes, then discard the number.
* developer must make sure search the card # in the array efficient. 
Obviously, the more card in the array, the more likely duplicate happen. It is non-deterministic 
on how long will this process end. 

The first try does not works very well, requires dozens line of code and performance is not 
that greate. It is time refactoring. This time we use JAVA 8.

### Method 1: The direct translate business requirement to a program statement. 
```JAVA
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
``` 

The 3rd line create a PrintWrite from a file so we can write the number to it. 
The 5th line is the meat. It directly translate from business requirement. Let's have a close
look. 

* "rn.logns(origin, origin+size)". Here the origin is start card#, the origin+size is the 
upper bound of the card number exclusively. This segement genreates a series of randome 
card# without Mod10 checksum digits. 
* ".distict()" elminates the duplicated. 
* ".map(Mod10Utils::appendMod10)"  appends the checksum digits to the card#. 
* ".limit(size)" limit the stream only generate "size" of card numbers. 
* "forEach(pw::println)" write the card# in text format to file. 

The whole business requirement is really just implemented in 2 statements. 

Unfortunately, this program run likes a snail. In my computer, it takes 1min9s to generated 
10 millions card# and write to file. It is also memory hog. out of the 4 mentods we 
developed, it got run out of memory exception with samllest card range.

 
### Method 2, Randomize the number by shuffling. 

```Java

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
```

In this version, first generate a sequece of card#, (line 4), then append the checksum digits,
(line 5), and finally convert it to a list (line 6)for later process. 

Next, we shuffle the card# (line 11).

At end, we write it to a file. (line 16).

This version improved the performance dramatically. Now it takes only 6.37 second. It can 
handle mroe cards as well. i.e. more memory efficient. And also the run time is deterministic.

Method3, Row power of classic programming. 

```Java
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

```

In this method, we do not use the Java 8 Stream and Java 7 Collection shuffle feature. We 
our own. But the high leve sequence is the same. i.e. 
* generate sequcne card#
* append digits. 
* shuffle & write in same iteration. 

Now the performance now further doubled from method 2, 23 time faster than method 1. The 
memory efficience further impreved since we do not have intermediate collenctions. 

Method 4, Futher optimization.

```Java
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
```

We made two optimization in this method.
* combine all steps in one big loop. slighly improve the performance, from 3.194s down 
to 2.971s
* store account# in array instead store full card# in array. This reduce the memory foot print
to half. 

### Which one do you like? 

The first method is the most concise and closest to business description, but it has really 
bad performace and sucks in memory usage. 

The last one has the best performance and the smallest memory usage, but is the least readable. 

The method 2 is somewhere in the between. 

Let me know which one you like. 

Stay tuned, we will have a second sprint. 
 
