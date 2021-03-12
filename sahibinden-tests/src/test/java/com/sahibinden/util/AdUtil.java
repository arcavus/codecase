package com.sahibinden.util;

import com.sahibinden.common.dto.ad.AdCreateRequest;
import com.sahibinden.common.dto.ad.AdResponse;
import com.sahibinden.common.dto.ad.Category;
import com.sahibinden.common.dto.ad.ClientType;
import com.sahibinden.common.service.AdService;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.IntStream;
import java.util.stream.LongStream;


public class AdUtil {

   public static String[] badWords = {"sikiiim", "pawn", "çük", "wiseasses", "auto erotic", "dickjuice", "tub girl", "coochie", "saksofon", "skyim", "knobead", "ananı", "wtf", "siktimin", "fistfucker", "punani", "rump", "dickdipper", "huur", "clitfuck", "doosh", "shagging", "snatch", "gtten", "pedophile", "nude", "jiz", "sokarm", "cumshots", "caca", "fukwit", "escort", "cock snot", "shit ass", "yarram", "big tits", "vjayjay", "sıçarım", "gotunden", "a$$", "penispuffer", "klan", "yaraksız", "sicarsin", "sand nigger", "fistfuck", "yaoi", "seks", "molest", "cumjockey", "siktir git", "orgasms", "donkeypunch", "bitching", "crackwhore", "one cup two girls", "bacy", "sambo", "kraut", "ovary", "zviyetini", "frenchify", "sik", "gotundeki", "ananın amı", "guido", "wanker", "pezevengin evladı", "sh1t", "sucks", "godamn", "hump", "hsktr", "bollock", "arse", "raunch", "sikeyim", "anası orospu", "fuckstick", "sikiym", "queerbait", "chode", "mutha", "fucka", "angut", "flog the log", "yarraimin", "twinkie", "jerkass", "sikienler", "uzi", "yarran", "fuck-ass", "tea bagging", "allah", "ninny", "siktiim", "sikici", "sex", "fistfucking", "cuntface", "asspirate", "sikimle", "c-0-c-k", "ball gravy", "koyiim", "dolcett", "vajinanı", "anan", "kavat", "ghay", "nonce", "skcem", "assclown", "yellow showers", "sksin", "picler", "sikişen", "reich", "shit", "gavad", "cyberfucking", "bitchers", "dingleberry", "bullet vibe", "fukker", "knobend", "oruspu", "boooobs", "sokum", "bodily", "bawdy", "liboş", "fags", "pisses", "queero", "kaka", "cum chugger", "fvck", "oç", "gang-bang", "jizz", "haysiyetsiz", "Doublelift", "cumbubble", "masterbations", "seaman", "amq", "amckta", "sikerler", "yarrağ", "niglet", "fook", "muff", "vagina", "xrated", "kaşar", "yarrağım", "fuck", "amın oğlu", "hootch", "ovums", "fagg", "glans", "cut rope", "cox", "sikismis", "serefsiz", "ziksiiin", "spik", "gey", "diligaf", "fleshflute", "cockblock", "gayfuck", "knobed", "ananz", "bung hole", "amsiz", "cockmongler", "whoralicious", "aminakoyarim", "d0uch3", "sikildiini", "sikim", "yaramn", "shaggin", "hitler", "sikinden", "bullshitted", "horny", "clitoris", "negro", "cums", "sikik", "sikseyidin", "chota bags", "shitblimp", "goregasm", "v1gra", "ananısikerim", "dumass", "strappado", "Fuck off", "oğlancı", "hardcoresex", "whoreface", "paedophile", "gtnde", "coochy", "shiznit", "koyyim", "a54", "sikem", "amk", "osururum", "kyke", "götoş", "babeland", "babaannesi kaşar", "peckerhead", "sikilesice", "puşt", "femdom", "flaps", "scum", "motherfucks", "coksucka", "cockmonkey", "dick shy", "crabs", "shitstain", "blow your load", "penis", "niggaz", "cyberfuck", "fistfucked", "assjacker", "d0ng", "nipples", "dommes", "ejakulate", "godumun", "amm", "cockmunch", "busty", "carpetmuncher", "orally", "gonads", "aminoglu", "orospucocugu", "doggy-style", "kinbaku", "sikiyorum", "cuntlicking", "jack off", "scat", "faggots", "ebeninki", "whores", "blowjob", "gotune", "crappy", "cockmaster", "Sandler", "pissin", "spade", "bokubokuna", "double dong", "cumdumpster", "heshe", "girl on", "viagra", "anasının am", "trashy", "smutty", "shamedame", "tight white", "titi", "bokbok", "suicide girls", "bareback", "yılışık", "sikseydin", "dlck", "a55hole", "dinks", "poonani", "queerhole", "yaraminbasi", "jackasses", "bootie", "sikersin", "ananı sikerim", "shemale", "bunny fucker", "domaltip", "dicktickler", "fingerfucks", "cleveland steamer", "guro", "ponyplay", "orospunun evladı", "amık", "c.o.c.k.", "schizo", "spooge", "doochbag", "gotunu", "cuntlicker", "fistfucks", "punkass", "assbanger", "cuntlick", "prickteaser", "beastial", "rapey", "siktirgit", "s-h-i-t", "mo-fo", "facial", "gassy ass", "boka", "whore", "anamla", "sikleriii", "c-u-n-t", "ibinenin", "pisspig", "masterbating", "one guy one jar", "pikey", "kunt", "nutter", "ejaculate", "siksinbaya", "vag", "sandbar", "nigg3r", "booooooobs", "dölü", "aminda", "sikiirken", "jackass", "dickmonger", "yarramn", "baby juice", "godoş", "double penetration", "fubar", "cunthole", "rtard", "koyiiym", "cockface", "blow mud", "hardcore", "m-fucking", "penile", "aminakoyim", "ma5terbate", "wog", "clitorus", "wench", "wank", "zikeyim", "tainted love", "faggs", "sikerim", "group sex", "gotelek", "siksok", "cocksniffer", "amınoğlu", "tittiefucker", "honky", "dickbag", "idiyot", "raping", "jerk-off", "mothafucks", "dildo", "douchebag", "salak", "ecchi", "abazan", "taşak", "gokkun", "humped", "götveren", "fingerfucked", "panty", "sikitiin", "ball gag", "knob", "dickfuck", "shibari", "wang", "cock", "cockholster", "douch3", "gebermiş", "cumdump", "yavşaktır", "cum", "sikey", "gerizekali", "titfuck", "f u c k e r", "big breasts", "bang (one's) box", "fooker", "pissed", "bumblefuck", "shitting", "wet dream", "pusse", "yarrak", "pissers", "coital", "dickish", "jack-off", "transsexual", "lavuk", "boozer", "paki", "zikiim", "bollox", "choade", "leather straight jacket", "dykes", "masterbation", "faggotcock", "twunter", "bi+ch", "jiggerboo", "cocksucking", "phuking", "hoor", "queers", "dirty", "boned", "buttcheeks", "dago", "s1kerm", "freex", "shiteater", "dagos", "anayin", "bust a load", "stiffy", "kerhane", "cumming", "juggs", "motherfuckings", "pube", "amın feryadı", "f-u-c-k", "mothafucking", "anann", "sakso", "pezevek", "dickzipper", "c0cksucker", "lesbos", "am biti", "reverse cowgirl", "strip", "dog style", "avrat", "m0fo", "raper", "style doggy", "pubes", "baby batter", "need the dick", "fuker", "nsfw images", "pipi", "shitbrains", "hard core", "prince albert piercing", "amına sokam", "motherfucker", "bdsm", "whorehopper", "twat", "masterb8", "beyinsiz", "fingerbang", "retard", "ayklarmalrmsikerim", "şerefsiz", "bokhu", "god-damned", "tw4t", "gspot", "ghey", "yogurtlayam", "ipne", "loin", "sh!t", "taff", "yarraminbaşı", "erotism", "cunt-struck", "fingerfuckers", "felcher", "bangbros", "gayass", "mafugly", "cocksmoke", "gippo", "gavat", "faigt", "hassittir", "phuq", "godsdamn", "testis", "bitch", "lesbian", "dong", "fucktards", "hemp", "b17ch", "fartknocker", "amina koyarim", "orospular", "flamer", "amina", "gebermek", "feltch", "cervix", "götü", "dumshit", "tit wank", "bitcher", "amını", "erection", "imansz", "sike", "white power", "wiseass", "sleaze", "prostitute", "s1krm", "mcik", "kahpenin feryadı", "s0b", "cumstain", "fuckwad", "virgin", "dickfucker", "huge fat", "rosy palm", "shittiest", "sikecem", "knobjocky", "pimp", "eat a dick", "goatcx", "retarded", "dirty sanchez", "cock-sucker", "bitch tit", "oc", "junkie", "feist", "kahpenin", "amcktan", "sksz", "asswipes", "tied up", "dick-sneeze", "bestial", "two fingers with tongue", "screw", "orospu", "sikien", "honkey", "mofo", "jiggaboo", "sikisek", "kooches", "fisting", "annesiz", "siktiminin", "girl on top", "işerim", "dingleberries", "b1tch", "vodka", "goodpoop", "kavatn", "pezevenk", "motherfuck", "domalsın", "cunt hair", "cum guzzler", "cut rope", "dominatrix", "menstruate", "porchmonkey", "son of a bitch", "cawk", "amcığınızı", "reefer", "vorarephilia", "blue waffle", "clit", "gerızekalı", "mother fucker", "bastardo", "barenaked", "beef curtains", "neonazi", "shitbagger", "kunilingus", "anasi", "nappy", "cyberfucked", "cipa", "bookie", "swinger", "breasts", "fuckoff", "bok", "gibmek", "kumming", "lesbo", "aeolus", "cummer", "siker", "gang bang", "ag", "fxck", "c-o-c-k", "bender", "pimpis", "kukudaym", "pigfucker", "oğlu it", "child-fucker", "cif", "womb", "salaak", "amına sikem", "nazism", "mick", "shaved beaver", "cum guzzler", "m0f0", "gang-bang", "sokarım", "diddle", "urinal", "chick with a dick", "rectus", "spiks", "domal", "sikisen", "mound of venus", "orospunun", "ejaculating", "masturbation", "osbir", "c.u.n.t", "he11", "phone sex", "strapon", "threesome", "sikinde", "shitter", "muff puff", "rape", "napalm", "kondum", "balls", "frigg", "humping", "areola", "mof0", "damn", "LEN", "titwank", "sissy", "sikt", "s.h.i.t.", "piece of shit", "fuck trophy", "shitfuck", "fuck buttons", "skime", "twatty", "gtfo", "siktigimin", "ejaculates", "sokuyum", "missionary position", "bloody hell", "microphallus", "birdlock", "cock sucker", "punany", "penis", "motherfucked", "cockfucker", "raghead", "flog the log", "domalıyor", "god-dam", "knobjokey", "fux0r", "scrote", "cuntlicking", "sikish", "homodumbshit", "ejaculating", "tinkle", "frotting", "areole", "soxum", "rectal", "sokuş", "öşex", "babani", "allahsız", "has siktir", "masterbate", "porn", "nut sack", "siktiğimin", "shitted", "jizm", "orosbucocuu", "kwif", "dirsa", "sevgi koyarım", "nazi", "beeyotch", "fuck yo mama", "shithole", "wh0re", "lusty", "dp action", "assbite", "ass fuck", "sumofabiatch", "pic", "amcuk", "pussys", "hasiktir", "sikime", "buceta", "s&m", "mother fucker", "amuğa", "fuckin", "cunnie", "cumdump", "sh!+", "aq.", "cunt hair", "sexy", "douchey", "doggie-style", "fingerfuck", "cocksucks", "ejaculatings", "porno", "yalarım", "mothafuck", "amna", "götün", "kafasiz", "vulva", "tard", "domaltmak", "fistfucked", "cum freak", "fucks", "rubbish", "penial", "yeasty", "vixen", "goddammit", "siktiririm", "taaklarn", "fack", "vajayjay", "bampot", "shitings", "gaysex", "koduğmun", "testical", "wedgie", "fistfucking", "cocaine", "osur", "tubgirl", "mothafuckin", "condom", "yarraak", "siki", "fuckmeat", "fuck trophy", "sikimden", "bint", "smegma", "alabama hot pocket", "dick shy", "kootch", "cockeye", "raging boner", "booby", "siktiğiminin", "minaamcık", "nimphomania", "siksinler", "pubic", "malafat", "sikdiğim", "kwif", "darn", "cibilliyetini", "poof", "dickflipper", "shiting", "pisliktir", "zikim", "jackoff", "assho1e", "gotlalesi", "unclefucker", "nigaboo", "doggy style", "hassikome", "amnda", "anasını", "deepthroat", "coons", "munter", "dallama", "slave", "p.u.s.s.y.", "shitheads", "mcfagget", "fingerfucked", "fecal", "sausage queen", "asshole", "homey", "opium", "titty", "fux", "domalan", "bastard", "fuks", "donkey punch", "mothafuckaz", "pansy", "autoerotic", "knobbing", "biatch", "amsz", "clitface", "porno", "choad", "cuntslut", "bitch tit", "tarrakimin", "dickhead", "amına koy", "jungle bunny", "boink", "heeb", "hussy", "lardass", "jerk off", "blow me", "amcık", "female squirting", "ahmak", "fuckbag", "dipshit", "cocksuka", "seduce", "pezo", "punta", "fcuker", "dalyarrak", "amcıklama", "fucktard", "slut bucket", "skiim", "butt-pirate", "coprolagnia", "moron", "rimjob", "orospu çocukları", "cuntbag", "ibnenin", "foobar", "siktir et", "cockmongruel", "boner", "beaver cleaver", "cacafuego", "queaf", "blonde action", "sikm", "kerhanelerde", "sausage queen", "handjob", "tosser", "fart", "fuk", "laciye boyadım", "rectum", "meme", "whoring", "eat a dick", "orgasm", "venus mound", "goyuyim", "herpes", "sikertir", "pedo", "nympho", "phukked", "yarragina", "fucknut", "slutdumper", "moolie", "koyayım", "foah", "testicle", "dirty pillows", "wetback", "nigg4h", "boktan", "nut butter", "sikiş", "geberik", "kayyum", "boklar", "weled", "chink", "tampon", "toots", "nimrod", "penisbanger", "hooch", "ass-hat", "aq", "motherfuckin", "shi+", "cuntlicker", "göt oğlanı", "injun", "date rape", "jerked", "orgies", "kancik", "gangbanged", "a_s_s", "kums", "shittings", "sikilmi", "amını s", "sikeydim", "fistfucks", "ibnesi", "penetrate", "anani sikeyim", "massa", "taşşak", "rosy palm and her 5 sisters", "tipinizi s.keyim", "sikilmis", "mafugly", "yarramin", "tart", "lusting", "kahpe", "attırdığım", "sikişme", "sikimiin", "faggot", "kafir", "jackhole", "Dumbcunt", "quicky", "chincs", "masturbate", "dangalak", "siksin", "ıbnelık", "mothafucka", "kafam girsin", "queer", "OÇ", "lezza/lesbo", "pedobear", "sikis", "fucktwat", "doggiestyle", "xxx", "ass hole", "smeg", "ananı", "hooker", "bastard", "memelerini", "atkafası", "assbang", "leather restraint", "bloodclaat", "scantily", "cockjockey", "phuks", "gassy ass", "itoğlu it", "cuntlick", "fatass", "fistfuckings", "cunt-struck", "diktim", "son-of-a-bitch", "skmek", "pussies", "whored", "tits", "jelly donut", "eben", "duche", "gaybob", "g-spot", "wigger", "dipship", "s-h-1-t", "f u c k", "sodomy", "camwhore", "anandan", "dicksipper", "assmonkey", "queef", "coffin dodger", "beastiality", "cim", "ibnelri", "footjob", "fuckbrain", "foot fetish", "weewee", "slaleni", "ananın", "mr hands", "pleasure chest", "grope", "skeyim", "soktuğumunun", "azdırıcı", "erect", "fucking", "pee", "dog-fucker", "pust", "gangbang", "kerane", "omorashi", "fingerfuck", "pussy", "tushy", "anal impaler", "göt verir", "cocksmoker", "kummer", "dickripper", "hymen", "skerm", "buttmunch", "masochist", "sikesicenin", "fucktoy", "cum freak", "clusterfuck", "coccydynia", "suck", "domalık", "dickhole", "yaaraaa", "ginger", "gtne", "pollock", "cop some wood", "chesticle", "dicksucker", "assmaster", "ball licking", "kodumunun", "porch monkey", "pussi", "gotlu", "spunk", "polack", "fist fuck", "gfy", "fuckhead", "sikimi", "prude", "asslicker", "şıllık", "scag", "munging", "sikiiimmm", "kodumun", "annenin", "5hit", "shitface", "snuff", "ball kicking", "gender bender", "erotic", "gooks", "siken", "hoşafı", "cockass", "biting", "shitters", "zibbi", "c.0.c.k", "sodomize", "slope", "toke", "dookie", "slutbag", "azdır", "shag", "pussy fart", "yarragimi", "pedophiliac", "yiffy", "intercourse", "hard on", "gtn", "kawk", "cock snot", "pot", "douche-fag", "cunt", "mna", "rahminde", "pici", "cracker", "kill", "ham flap", "amına koyarım", "tit wank", "bullshits", "dvda", "creampie", "pthc", "kappe", "muthafecker", "fcuking", "minge", "potty", "bacının", "fuck hole", "gash", "boobs", "junglebunny", "fuckmeat", "sexo", "ass-pirate", "assbag", "goyiim", "ball sucking", "punanny", "fuck you", "ocuun", "ibina", "cornhole", "wh0reface", "lesbians", "looney", "assbanged", "orostopol", "beotch", "pissoff", "dkerim", "amindan", "pezevengi", "a$$hole", "gotveren", "sokayım", "ossuruk", "willies", "ibneni", "dick hole", "zibidi", "two girls one cup", "bacini", "osurduu", "amın oglu", "skullfuck", "amina g", "camslut", "amısına", "brunette action", "sktrr", "cyberfucked", "labia", "bastards", "sikkim", "amiyum", "boiolas", "pillowbiter", "gay", "weed", "mothafucked", "fucknugget", "hotsex", "fagtard", "asscock", "amngtn", "iberian slap", "rusty trombone", "zikiiim", "scrotum", "cunilingus", "spook", "beardedclam", "asswad", "amına koyyim", "fukwhit", "bızır", "fagot", "analprobe", "shitass", "aminako", "pcp", "fingerfucks", "ibneleri", "assholes", "sod off", "muthafuckker", "tribadism", "xx", "faggit", "wop", "weledizina", "cameltoe", "gringo", "sikti", "x-rated", "fannybandit", "ibnelik", "shit fucker", "bacağına sıçayım", "fuckbutter", "wanky", "poopchute", "booze", "cockknocker", "nambla", "ziksiin", "bbw", "fuckheads", "bacına", "anam", "ejaculates", "bootee", "pissflaps", "ejaculated", "undressing", "panties", "chi-chi man", "pegging", "chinky", "yalama", "cockmuncher", "smartasses", "frigga", "a.q.", "w00se", "tramp", "yarra", "amindayken", "chodes", "taig", "assfuck", "sikin", "amısını", "götlek", "booty call", "uterus", "hebe", "shitey", "pubis", "fellate", "wankjob", "asses", "prig", "sevişelim", "stupid", "ballbag", "cockbite", "blue waffle", "amınakoyim", "bombok", "sadism", "skecem", "dickweed", "hooters", "fagbag", "sluts", "gaydo", "anilingus", "cum dumpster", "yoğurtlayam", "arrse", "poop", "bitched", "he-she", "yobbo", "orospu çoc", "assface", "cumguzzler", "sucking", "muffdiver", "cahone", "hoer", "muff puff", "siktir", "sulaleni", "psycho", "fuckhole", "clunge", "figging", "tit", "willy", "sie", "dickweasel", "dawgie-style", "gaytard", "dickmilk", "scissoring", "am", "fistfuckings", "nig nog", "piçin oğlu", "bum", "dillweed", "ocuu", "slope", "fellatio", "shited", "orrospu", "santorum", "dildo", "gebertir", "dendrophilia", "dingil", "peepee", "jock", "sürtük", "amcklama", "fuck yo mama", "anneni", "Cocklump", "dickwhipper", "donkeyribber", "essohbee", "cumslut", "dickheads", "amcığını", "fucked", "pussy fart", "zubb", "acrotomophilia", "extacy", "yarragm", "hoar", "pipiş", "sikeym", "poon", "testes", "sikine", "amcıklandı", "cockknoker", "assmucus", "mothafucker", "phuked", "siktim", "sikmisligim", "sokarim", "gigolo", "fingerfucking", "shirt lifter", "eunuch", "vulgar", "analarn", "corp whore", "tranny", "domination", "how to murdep", "ass", "daltassak", "fice", "oğlan", "cyberfuc", "bullshit", "fuck", "son of a motherless goat", "heroin", "lube", "slanteye", "sleazy", "gtnden", "sülalenizi", "t1tties", "chinc", "amk çocuğu", "ma5terb8", "cus", "tasak", "asssucker", "leper", "bitchin", "geber", "clit licker", "cripple", "doofus", "assbandit", "fondle", "sultry women", "trumped", "panooch", "chota bags", "shits", "dassagi", "domaldı", "ananin", "bacn", "sikesen", "dry hump", "alaskan pipeline", "feltcher", "boozy", "mal", "koyum", "gaylord", "yarrağımı", "assmuncher", "ammak", "o. çocuğu", "sikimde", "lovemaking", "buttplug", "gays", "asswhole", "dickwod", "knobhead", "sikiir", "motherfucker", "fingering", "daterape", "a.q", "urine", "assgoblin", "ham flap", "fuckboy", "orospu çocuğu", "fistfucker", "slutkiss", "sikleri", "sikilsin", "twunt", "nigger", "bestiality", "souse", "whorebag", "douche", "d1ldo", "div", "coprophilia", "babanı", "sıecem", "choade", "prick", "sıçtığım", "taaklarna", "ananın dölü", "midget", "orospuçocuğu", "lust", "hot carl", "dingilini", "motherfucking", "yid", "tipini s.k", "crack", "skik", "bukkake", "ebenin", "pissing", "rum", "sikiim", "zulliyetini", "verdiimin", "beatch", "stfu", "sktiimin", "sülaleni", "kinkster", "weirdo", "hell", "cunillingus", "punky", "fenian", "ecdadini", "sikimsonik", "piss-off", "golden shower", "screwing", "ritard", "motherfucka", "fistfuckers", "pedophilia", "suckass", "weiner", "t1t", "assfucker", "bitch", "ananısikeyim", "masterbat*", "n1gga", "domaltarak", "tittywank", "mezveleli", "voyeur", "babanın", "strap on", "teets", "weenie", "kock", "puss", "siktirir", "corpulent", "twatwaffle", "sittimin", "fuckbutt", "assshit", "ossurmak", "shittier", "hassiktir", "yarragi", "cum dumpster", "fuck-bitch", "titties", "feck", "cop some wood", "pussylicking", "cl1t", "asswipe", "kevase", "fanny", "bescumber", "fudge-packer", "kevvase", "twink", "monakkoluyum", "siksiz", "fuckwit", "pissin", "dike", "kaypak", "amcık hoşafı", "kum", "bokum", "titt", "milf", "h0mo", "amınako", "middle finger", "fukkin", "dönek", "darkie", "fuck-tard", "zikik", "b!tch", "pron", "amcklaryla", "gay sex", "how to kill", "yarrrak", "c0ck", "f_u_c_k", "shithouse", "xikeyim", "throating", "fuckme", "eat hair pie", "rimming", "bacını", "orospudur", "gaywad", "vibrator", "niggas", "sexs", "corp whore", "osuruk", "siksem", "karhane", "jerk", "sokuk", "buttfucker", "nobjokey", "zoophilia", "bum boy", "piss pig", "s hit", "emi", "whoar", "amona", "dimwit", "fuckings", "revizyonist", "poop chute", "blumpkin", "twathead", "bellend", "fist fuck", "damned", "anaaann", "fuckface", "asscracker", "choc ice", "swastika", "horniest", "barf", "urethra play", "hentai", "fecker", "amlarnzn", "embesil", "window licker", "bimbo", "amsız", "idiot", "sokarmkoduumun", "floozy", "giberler", "ass-jabber", "butt", "git", "topsun", "strip club", "clitty litter", "bitches", "cibiliyetsiz", "pissed off", "feriştah", "organ", "fagfucker", "ahole", "splooge", "sikli", "menage a trois", "jigaboo", "dumbass", "lolita", "goddam", "bitchy", "dammit", "anal", "pantie", "amckl", "penisfucker", "cock pocket", "booger", "cikar", "domaldın", "booooobs", "shitdick", "sikilmie", "fag", "dick", "ambiti", "piss off", "phuck", "spic", "puto", "götoğlanı", "sanger", "4r5e", "götüne", "sikiyim", "two fingers", "girls gone wild", "racy", "amına koyayım", "assnigger", "commie", "kunja", "niggers", "dyke", "assbangs", "fuck hole", "hom0", "giant cock", "azdım", "shit fucker", "shaved pussy", "old bag", "golliwog", "tities", "sokam", "auzlu", "doggystyle", "penetration", "sittir", "yarraam", "twats", "dickslap", "mudik", "yarak", "Breeder", "teabagging", "assfukka", "2 girls 1 cup", "nig-nog", "ejaculation", "orospuyuz", "orgy", "götünü", "dirty Sanchez", "jerk0ff", "towelhead", "soused", "hookah", "blumpkin", "bod", "pole smoker", "azz", "gtelek", "azazel", "seamen", "bokkkumu", "goddamned", "buttfucka", "t1tt1e5", "gtveren", "ugly", "gibis", "thrust", "asshopper", "gibiş", "pisses", "ecdadını", "cenabet", "whorealicious", "manyak", "motherfuckka", "poontang", "sekis", "amina k", "dick hole", "spac", "batty boy", "yarraaaa", "krar", "puşttur", "d1ld0", "coonnass", "loins", "sandbar", "cumshot", "rapist", "dicksucking", "sksn", "koduğmunun", "coon", "göt", "veled", "domaltıp", "siktirolgit", "fuck puppet", "brown showers", "beaver lips", "lmao", "skag", "phonesex", "pms", "amina koyayım", "fudgepacker", "nudity", "domaltırım", "bitchtits", "bollocks", "shitcanned", "sikenin", "flange", "veledizina", "cocks", "skank", "amcığın", "axwound", "amcığı", "boners", "goyyim", "reetard", "pissoff", "gooch", "bunghole", "cok", "amin oglu", "amarım", "tassak", "siktigiminin", "tongue in a", "götünekoyim", "big knockers", "orgasim", "shitcunt", "mothafuckas", "murder", "taking the piss", "p0rn", "fuckwhit", "jerkoff", "cyalis", "assshole", "cunny", "piçler", "toplarm", "gibtiler", "cyberfuckers", "mong", "ballsack", "fuckwitt", "l3i+ch", "gerizekalı", "iap", "mothafucked", "master-bate", "tiyniyat", "barely legal", "yavş", "sikko", "sikdi", "göt veren", "homoerotic", "buncombe", "fagged", "taste my", "big black", "felching", "cunts", "cibilliyetsiz", "amck", "bullturds", "bloody", "bosom", "tawdry", "quim", "carpet muncher", "skem", "r-tard", "kikes", "cuntsicle", "kaltak", "bitchass", "fukkers", "kooch", "f4nny", "extasy", "pezeven", "kondums", "sikini", "götten", "oruspuçocuğu", "skeet", "koca göt", "s.o.b.", "yavşak", "anani", "arsehole", "anuna", "smartass", "bong", "butt plug", "jap", "ferre", "bull shit", "orospu cocugu", "sikicem", "goddamn", "assh0le", "wrinkled starfish", "booobs", "cumtart", "apeshit", "menstruation", "fucktoy", "götlalesi", "bacndan", "fanyy", "circlejerk", "blow job", "ananın am", "siktiiminin", "moo moo foo foo", "shiz", "smut", "corksucker", "scrud", "lech", "ana", "ass fuck", "dickface", "knob end", "yalarun", "cuntsicle", "herp", "piss", "meth", "babası pezevenk", "tittyfucker", "amina koyayim", "jagoff", "amini", "scrog", "stroke", "menses", "butt fuck", "fucker", "felch", "fucking", "s-o-b", "omg", "cocksuck", "whore", "muff diver", "dumbfuck", "kancık", "fingerfucking", "siksz", "lezzie", "l3itch", "saxo", "masturbating", "herpy", "spick", "asshead", "m45terbate", "boong", "sniper", "dicks", "orgasmic", "cuntrag", "koyarm", "male squirting", "lameass", "faig", "prod", "amndaki", "gerzek", "dumb ass", "assmucus", "pornography", "assmunch", "goyum", "make me come", "ebeni", "öküz", "sker", "dingle", "fuckedup", "dumbasses", "fucknutt", "snowballing", "fagots", "raped", "mutherfucker", "f.u.c.k", "beef curtain", "renob", "shrimping", "hödük", "hot chick", "cretin", "damnit", "yarraamı", "göt herif", "clitty", "teste", "dink", "beaner", "deggo", "yavuşak", "booty", "siktiimin", "orospuydu", "jail bait", "phuk", "asshat", "holy shit", "ammna", "atmık", "fuckers", "boobies", "fuq", "patlak zar", "cocknose", "ho", "boob", "pussy", "homo", "phukking", "fannyfucker", "pussy palace", "ananızın am", "sikmek", "ruski", "master-bate", "cockhead", "shitty", "orospu çocuğudur", "domaltır", "fuckersucker", "nigga", "s_h_i_t", "cocksuckers", "mothafuckers", "queaf", "cock pocket", "dildos", "junky", "fisted", "sandnigger", "ovum", "genitals", "drunk", "n1gger", "fahise", "spread legs", "sokaym", "ossurduum", "god damn", "fahişe", "crotte", "Poopuncher", "gai", "domalmak", "shitters", "sikmem", "ball sack", "wrapping men", "cyberfucking", "testee", "asslick", "ar5e", "anani sikerim", "mincikliyim", "jizzed", "teez", "a2m", "faggitt", "fingerfucker", "douchebags", "sikilmiş", "motherfuckers", "cocknugget", "slut", "shithead", "thundercunt", "bimbos", "nobhead", "cummin", "cooter", "brotherfucker", "yarragindan", "dogging", "h0m0", "dalyarak", "siktii", "clitty litter", "siktiriyor", "godamnit", "fuck puppet", "beaners", "arian", "veqtable", "ibne", "valium", "ananisikeyim", "fisty", "slag", "siktir ol git", "unwed", "bastinado", "d1ck", "yilisik", "s1kerim", "doggie style", "dalaksız", "kafasız", "shitfaced", "nob", "bosalmak", "siktiler", "perversion", "cocksukka", "vomit", "anas", "zıkkımım", "amugaa", "düdük", "a55", "hayvan herif", "ibnedir", "blowjobs", "wad", "nawashi", "niggah", "homoey", "v14gra", "ass-fucker", "kike", "piç", "boku", "shota", "yaraktr", "hun", "ebleh", "skerim", "domalmış", "ninnyhammer", "eat my ass", "ağzına sıçayım", "cockburger", "aminiyarraaniskiim", "ananı sikeyim", "maxi", "teat", "sikmiler", "fuck-ass", "anal leakage", "siktir lan", "goatse", "tittyfuck", "screwed", "göt deliği", "amına", "nob jokey", "sikil", "abaza", "twatlips", "jism", "fuckin", "orgasims", "stoned", "cocksuck", "pecker", "upskirt", "dick head", "cnut", "fucktart", "amlı", "hore", "basur", "anus", "gayfuckist", "goddamnit", "sadist", "camgirl", "scroat", "oruspu çocuğu", "cocksucks", "cunnilingus", "yarrana", "attrrm", "goldenshower", "slut bucket", "black cock", "bondage", "bulldyke", "fistfuckers", "jizm", "climax", "zigsin", "wazoo", "whorehouse", "dumbshit", "tittie5", "aryan", "undies", "clits", "amcik", "doggin", "bumclat", "ganja", "amına s", "kinky", "futanari", "sucked", "cyberfucker", "yararmorospunun", "cocksucked", "anay", "clover clamps", "nut butter", "minger", "schlong", "ananisikerim", "eat hair pie", "piç kurusu", "turd", "lez", "fuck", "lemon party", "bokça", "fucker", "rimjaw", "sikimtrak", "cum chugger", "goddamn", "sikertmek", "d0uche", "siktim", "inbred", "need the dick", "pezeveng", "beaver", "jaggi", "hand job", "fingerfucker", "boner", "lmfao", "skim", "mams", "cocain", "b00bs", "cocksmith", "gangbangs", "japs", "fuckup", "foad", "fannyflaps", "yaram", "buttmuch", "madafaka", "bigtits", "urophilia", "siktiğim", "hoe", "cornhole", "muther", "shitspitter", "shitbreath", "numbnuts", "2g1c", "cyberfuck", "pussy palace", "butt fuck", "goo girl", "gook", "amteri", "poonany", "assfaces", "koyim", "octopussy", "cockwaffle", "polesmoker", "phallic", "naked", "foreskin", "giberim", "clit licker", "beef curtain", "nipple", "vajina", "aptal", "malak", "hoare", "anana", "ibnerator", "cunthunter", "cocksucker", "camel toe", "cuntass", "sikip", "whiz", "rumprammer", "otuzbir", "sikimin", "playboy", "jiz", "fuc", "totoş", "ananınki", "semen", "pisser", "masterbat3", "amnn", "bugger", "cokmuncher", "yavak", "chocolate rosebuds", "hircismus", "pornos", "blonde on blonde action", "yaraaam", "cocksucked", "buttfuck", "dickbeaters", "fudge packer", "hooter", "dickwad", "veled i zina", "bung", "bollok", "douchewaffle", "kevaşe", "mothafuckings", "götüne koyim", "scrot", "fuckme", "orostoban", "shagger", "domalt", "amuna", "ibine", "gonad", "amn", "god", "revue", "nobjocky", "muffdiving", "ass", "cuntbag", "shitt", "fuck-bitch", "koduumun", "sokiim", "jailbait", "opiate", "dummy", "sodom", "splooge moose", "gotten", "anal", "va-j-j", "shite", "sperm", "bummer", "bust a load", "violet wand", "anasinin", "crikey", "niggle", "nutsack", "hobag", "5h1t", "cockshit", "hiv", "fuckingshitmotherfucker", "son of a whore", "pinko", "crap", "boşalmak", "gae", "fagging", "pussypounder", "iserim", "amkafa", "shitbag", "mothafucking", "götelek", "ananızın", "deep throat", "thug", "bosomy", "topless", "dinsiz", "nymphomania", "incest", "yrrak", "pricks", "sexual", "how to murder", "shitfull", "tush", "fuckass", "dopey", "butthole", "dick-ish", "fcuk"};


   public static AdCreateRequest getRandomAdCreateRequest() {
      AdCreateRequest adCreateRequest = new AdCreateRequest();
      adCreateRequest.setBidPrice(RandomUtils.nextLong(50L, 300L));
      adCreateRequest.setFrequencyCapping(RandomUtils.nextLong(6, 24));
      adCreateRequest.setLocations(getRandomLocations());
      adCreateRequest.setClientType(getRandomClientType());
      adCreateRequest.setTotalBudget(adCreateRequest.getBidPrice() * RandomUtils.nextInt(15,100));
      adCreateRequest.setCategoryList(getRandomCategories());
      adCreateRequest.setTitle(generateRandomStringDoesntContainsBadWord(12));
      adCreateRequest.setDescription(generateRandomStringDoesntContainsBadWord(50));
      adCreateRequest.setLink("http://www.example.com");
      return adCreateRequest;
   }

   private static List<Long> getRandomLocations() {
      Set<Long> locations = new HashSet<>();
      LongStream.range(0, RandomUtils.nextInt(1, 10)).forEach(i -> locations.add(getRandomLocation()));
      return new ArrayList<>(locations);
   }

   private static Long getRandomLocation() {
      return RandomUtils.nextLong(1, 81);
   }


   private static ClientType getRandomClientType() {
      int random = RandomUtils.nextInt(0, 4);

      if (ClientType.ANDROID.ordinal() == random) return ClientType.ANDROID;
      if (ClientType.DESKTOP.ordinal() == random) return ClientType.DESKTOP;
      if (ClientType.IOS.ordinal() == random) return ClientType.IOS;
      if (ClientType.MOBILE_WEB.ordinal() == random) return ClientType.MOBILE_WEB;

      return null;
   }

   private static List<Category> getRandomCategories() {
      Set<Category> categories = new HashSet<>();

      int categoryCount = RandomUtils.nextInt(1, 4);
      IntStream.range(0, categoryCount).forEach(i ->
              categories.add(getRandomCategory())
      );
      return new ArrayList<>(categories);
   }


   private static Category getRandomCategory() {
      int random = RandomUtils.nextInt(0, 100) % 4;

      if (Category.EMLAK.ordinal() == random) return Category.EMLAK;
      if (Category.HAYVANLAR_ALEMI.ordinal() == random) return Category.HAYVANLAR_ALEMI;
      if (Category.IKINCI_EL.ordinal() == random) return Category.IKINCI_EL;
      if (Category.VASITA.ordinal() == random) return Category.VASITA;

      return null;
   }


   public static ConcurrentLinkedQueue<String> generateRandomVisitors(Integer visitorCount) {
      ConcurrentLinkedQueue<String> visitors = new ConcurrentLinkedQueue<>();
      for (int i = 0; i < visitorCount; i++) {
         visitors.add(RandomStringUtils.randomAlphanumeric(10));
      }
      return visitors;
   }


   public static String getRandomVisitor(ConcurrentLinkedQueue<String> visitors) {

      return visitors.poll();

   }


   public static AdWithActualStat getRandomAd(List<AdWithActualStat> adWithActualStats) {
      if (adWithActualStats.size() == 0) return null;
      if (adWithActualStats.size() == 1) return adWithActualStats.get(0);
      return adWithActualStats.get(RandomUtils.nextInt(0, adWithActualStats.size()));
   }


   // If input is 90, 90% it will return true ,10 percent it will return false;
   public static boolean withPercentage(Integer percentage) {
      int random = RandomUtils.nextInt(0, 100);
      return random <= percentage;
   }

   public static List<AdWithActualStat> generateRandomAds(int adCount, AdService adService) throws IOException {
      List<AdWithActualStat> adWithActualStats = Collections.synchronizedList(new ArrayList<>());
      for (int i = 0; i < adCount; i++) {
         AdCreateRequest adCreateRequest = getRandomAdCreateRequest();
         AdResponse adResponse = adService.createAd(adCreateRequest);
         AdWithActualStat adWithActualStat = new AdWithActualStat(adResponse, adCreateRequest, adService);
         adWithActualStats.add(adWithActualStat);
      }
      return adWithActualStats;
   }

   public static String generateRandomStringDoesntContainsBadWord(int size)
   {
      String str  = RandomStringUtils.randomAlphanumeric(size);

      if (containsBadWord(str)) return generateRandomStringDoesntContainsBadWord(size);

      return str;
   }

   private static boolean containsBadWord(String str)
   {

      for (String badWord : badWords) {

         if (str.contains(badWord)) return true;
      }
      return false;
   }
}
