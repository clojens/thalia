{{:version "1.5.1", :library "clojure.core", :language "en_US"}
 ({:symbol "==",
   :ns "clojure.core",
   :extended-docstring
   "(== x y) is true if x and y are both numbers, and represent\nnumerically equal values.  Unlike =, there are no separate\n'categories' of numeric values that are treated as always unequal to\neach other.  If you call == with more than two arguments, the result\nwill be true when all consecutive pairs are ==.  An exception is\nthrown if any argument is not a numeric value.\n\nExceptions, or possible surprises:\n\n* == is false for BigDecimal values with different scales, e.g. (==\n  1.50M 1.500M) is false.  http://dev.clojure.org/jira/browse/CLJ-1118\n* 'Not a Number' values Float/NaN and Double/NaN are not equal to any\n  value, not even themselves.\n\nExamples:\n\n    user=> (= 2 2.0)   ; = has different categories integer and floating point\n    false\n    user=> (== 2 2.0)  ; but == sees same numeric value\n    true\n    user=> (== 5 5N (float 5.0) (double 5.0) (biginteger 5))\n    true\n    user=> (== 5 5.0M) ; this is likely a bug\n    false\n    user=> (== Double/NaN Double/NaN)  ; this is normal\n    false\n    user=> (== 2 \"a\")\n    ClassCastException java.lang.String cannot be cast to java.lang.Number  clojure.lang.Numbers.equiv (Numbers.java:206)\n"}
  {:symbol "=",
   :ns "clojure.core",
   :extended-docstring
   "(= x y) is true if x and y are both:\n\n* numbers in the same 'category', and numerically the same, where\n  category is one of (integer or ratio), floating point, or\n  BigDecimal.  Use == if you want to compare for numerical equality\n  between different categories, or you want an exception thrown if\n  either value is not a number.\n* sequences, lists, vectors, or queues, with equal elements in the\n  same order.\n* sets, with equal elements, ignoring order.\n* maps, with equal key/value pairs, ignoring order.\n* symbols, or both keywords, with equal namespaces and names.\n* refs, vars, or atoms, and they are the same object, i.e. (identical?\n  x y) is true.\n* the same type defined with deftype.  The type's equiv method is\n  called and its return value becomes the value of (= x y).\n* other types, and Java's x.equals(y) is true.  The result should be\n  unsurprising for nil, booleans, characters, and strings.\n\nIf you call = with more than two arguments, the result will be true\nwhen all consecutive pairs are =.\n\nExceptions, or possible surprises:\n\n* When comparing collections with =, numbers within the collections\n  are also compared with =, so the three numeric categories above\n  are significant.\n* = is false for BigDecimal values with different scales, e.g. (=\n  1.50M 1.500M) is false.  http://dev.clojure.org/jira/browse/CLJ-1118\n* 'Not a Number' values Float/NaN and Double/NaN are not equal to any\n  value, not even themselves.  This leads to odd behavior if you use\n  them as set elements or map keys.\n\nExamples:\n\n    user=> (= 3 3N)   ; same category integer\n    true\n    user=> (= 2 2.0)  ; different categories integer and floating point\n    false\n    user=> (= [0 1 2] '(0 1 2))\n    true\n    user=> (= '(0 1 2) '(0 1 2.0))   ; 2 and 2.0 are not =\n    false\n\n    ;; While this map is similar to the vector in that it maps the\n    ;; same integers 0, 1, and 2 to the same values, maps and vectors\n    ;; are never = to each other.\n    user=> (= [\"a\" \"b\" \"c\"] {0 \"a\" 1 \"b\" 2 \"c\"})\n    false\n\n    user=> (= (with-meta #{1 2 3} {:key1 1}) (with-meta #{1 2 3} {:key1 2}))\n    true                  ; Metadata is ignored when comparing\n\n    user=> (= Double/NaN Double/NaN)  ; this is normal\n    false\n\n    user=> (def s1 #{1.0 2.0 Double/NaN})\n    #'user/s1\n    user=> s1\n    #{2.0 1.0 NaN}\n    user=> (contains? s1 1.0)         ; this is expected\n    true\n    user=> (contains? s1 Double/NaN)  ; this might surprise you\n    false\n"}
  {:symbol "apply",
   :ns "clojure.core",
   :extended-docstring
   "f is a function and the last argument args is a sequence.  Calls f\nwith the elements of args as its arguments.  If more arguments are\nspecified (x y ...), they are added to the beginning of args to form\nthe complete argument list with which f is called.\n\nExamples:\n\n    user=> (apply + [1 2])           ; same as (+ 1 2)\n    3\n    user=> (apply + 1 2 [3 4 5 6])   ; same as (+ 1 2 3 4 5 6)\n    21\n    user=> (apply + [])              ; same as (+)\n    0\n    ;; This doesn't work because and is a macro, not a function\n    user=> (apply and [true false true])\n    CompilerException java.lang.RuntimeException: Can't take value of a macro: #'clojure.core/and, compiling:(NO_SOURCE_PATH:1:1)\n"}
  {:symbol "compare",
   :ns "clojure.core",
   :extended-docstring
   "compare is the default comparator for sorting with sort and sort-by,\nfor ordering the elements of a sorted-set, and for ordering the keys\nof a sorted-map.  See (topic Comparators).\n\nAs for all 3-way comparators, it takes two arguments x and y.  It\nreturns an int that is negative if x should come before y, positive if\nx should come after y, or 0 if they are equal.\n\ncompare works for many types of values, ordering values as follows:\n\n* numbers: increasing numeric order, returning 0 if two numbers are\n  numerically equal by ==, even if = returns false\n* strings, symbols, keywords: lexicographic order (aka dictionary\n  order) by their representation as sequences of UTF-16 code units.\n  This is alphabetical order (case-sensitive) for strings restricted\n  to the ASCII subset.\n* vectors: shortest-to-longest, with lexicographic ordering among\n  equal length vectors.\n* All Java types implementing the Comparable interface such as\n  characters, booleans, File, URI, and UUID are compared via their\n  compareTo methods.\n* nil: can be compared to all values above, and is considered less\n  than anything else.\n\ncompare throws an exception if given two values whose types are \"too\ndifferent\", e.g. it can compare Integers, Longs, and Doubles to each\nother, but not strings to keywords or keywords to symbols.  It cannot\ncompare lists, sequences, sets, or maps.\n\nExamples:\n\n    user=> (sort [22/7 2.71828 Double/NEGATIVE_INFINITY 1 55 3N])\n    (-Infinity 1 2.71828 3N 22/7 55)\n\n    user=> (def sset1 (sorted-set \"aardvark\" \"boo\" \"a\"\n                                  \"Antelope\" \"bar\"))\n    #'user/sset1\n    user=> sset1\n    #{\"Antelope\" \"a\" \"aardvark\" \"bar\" \"boo\"}\n\nSee Java documentation of String's compareTo method for additional\ndetails on String comparison.\n\nSymbols are sorted by their representation as strings, sorting first\nby their namespace name, and if they are in the same namespace, then\nby their name.  If no namespace is included, those symbols will be\nsorted before any symbol with a namespace.  Keywords are sorted\nsimilarly to symbols.\n\n    user=> (def sset2 (sorted-set 'user/foo 'clojure.core/pprint 'bar\n                                  'clojure.core/apply 'user/zz))\n    #'user/sset2\n    user=> sset2\n    #{bar clojure.core/apply clojure.core/pprint user/foo user/zz}\n\n    user=> (def smap1 (sorted-map :map-key 10, :amp [3 2 1],\n                                  :blammo \"kaboom\"))\n    #'user/smap1\n    user=> smap1\n    {:amp [3 2 1], :blammo \"kaboom\", :map-key 10}\n\nVectors are sorted by their length first, from shortest to longest,\nthen lexicographically among equal-length vectors.\n\n    user=> (sort [[1 2] [1 -5] [10000] [4 -1 20] [3 2 5]])\n    ([10000] [1 -5] [1 2] [3 2 5] [4 -1 20])\n\nAn exception will be thrown if you call compare with different\ntypes (any numeric types above can be compared to each other, but not\nto a non-numeric type).  An exception will also be thrown if you use\ncompare on a list, set, map, or any other type not mentioned above.\nYou must implement your own comparator if you wish to sort such\nvalues.  See [Comparators in Clojure][Comparators] for examples of\ncomparators that can do this.\n\n    user=> (sort [5 \"a\"])\n    ClassCastException java.lang.Long cannot be cast to java.lang.String  java.lang.String.compareTo (String.java:108)\n    user=> (sort [:foo 'bar])\n    ClassCastException clojure.lang.Keyword cannot be cast to clojure.lang.Symbol  clojure.lang.Symbol.compareTo (Symbol.java:106)\n\n    user=> (sort [#{1 2} {2 4}])\n    ClassCastException clojure.lang.PersistentArrayMap cannot be cast to java.lang.Comparable  clojure.lang.Util.compare (Util.java:153)\n    user=> (sort [{:a 1 :b 3} {:c -2 :d 4}])\n    ClassCastException clojure.lang.PersistentArrayMap cannot be cast to java.lang.Comparable  clojure.lang.Util.compare (Util.java:153)\n\nImplementation detail: Clojure Refs can also be sorted using\ncompare.  They are sorted in the order they were created.\n"}
  {:symbol "contains?",
   :ns "clojure.core",
   :extended-docstring
   "It is a common mistake to think of the English meaning of the word\n'contains', and believe that therefore contains? will tell you whether\na vector or array contains a value.  See 'some' if that is what you\nwant.\n\ncontains? is good for checking whether a map has a mapping for a key,\nor a set contains an element.  It can be easier to use correctly than\n'get', especially if you wish to allow a key, value, or set element to\nbe nil.\n\nExamples:\n\n    user=> (contains? #{:a :b 5 nil} :b)       ; :b is in the set\n    true\n    user=> (contains? #{:a :b 5 nil} 2)        ; 2 is not\n    false\n    user=> (contains? #{:a :b 5 nil} nil)      ; nil is in this set\n    true\n    user=> (contains? #{:a :b 5} nil)          ; but not in this one\n    false\n\n    user=> (contains? {:a \"a\" nil \"nil\"} :a)   ; key :a is in the map\n    true\n    user=> (contains? {:a \"a\" nil \"nil\"} :b)   ; :b is not\n    false\n    user=> (contains? {:a \"a\" nil \"nil\"} nil)  ; nil is a key here\n    true\n    user=> (contains? {:a \"a\"} nil)            ; but not here\n    false\n\ncontains? also works for Java collections implementing interfaces\njava.util.Set or java.util.Map.\n\nIt is not as useful, but contains? can also determine whether a number\nlies within the range of defined indices of a vector, string, or Java\narray.  For strings and Java arrays, it is identical in these cases to\n(and (0 <= i) (< i (count coll))) where i is equal to (. key\nintValue).  The behavior is the same for vectors, except only integer\nvalues can return true.\n\n    user=> (contains? \"abcdef\" 5)\n    true       ; max string index is 5\n    user=> (contains? [:a :b :c] 1)\n    true       ; max vector index is 2\n    user=> (contains? (int-array [28 35 42 49]) 10)\n    false      ; max array index is 3\n\nThe intValue conversion can lead to unexpected behavior, demonstrated\nbelow.  This happens because intValue converts the long and double\nvalues shown to 0, which is in the range [0,2] of indices.\n\n    user=> (def long-truncates-to-int-0 (bit-shift-left 1 33))\n    user=> (contains? \"abc\" long-truncates-to-int-0)\n    true\n    user=> (contains? \"abc\" -0.99)\n    true\n    user=> (contains? [:a :b :c] long-truncates-to-int-0)\n    true\n    user=> (contains? [:a :b :c] 0.5)\n    false       ; only integer values can return true for vectors\n\nSee also: some, get\n"}
  {:symbol "get",
   :ns "clojure.core",
   :extended-docstring
   "'get' works for several types of arg 'map', not only maps:\n\n* maps, including records and Java objects implementing java.util.Map\n* sets, but not Java objects implementing java.util.Set\n* vectors, where the key is the index of the element to get\n* strings and Java arrays, where again the key is the index\n* nil, for which get always returns not-found or nil\n\nExamples:\n\n    user=> (get #{\"a\" 'b 5 nil} 'b)       ; symbol b is in the set\n    b\n    user=> (get #{\"a\" 'b 5 nil} 2)        ; 2 is not\n    nil\n    user=> (get #{\"a\" 'b 5 nil} nil)      ; nil is in this set, but one\n    nil        ; cannot distinguish this result from not being in the set.\n    user=> (get #{\"a\" 'b 5} nil)          ; Result is same here.\n    nil\n\nYou may specify a not-found value to help distinguish these cases.\nThis works well as long as there is some value you know is not in the\nset.\n\n    user=> (get #{\"a\" 'b 5} nil :not-found)\n    :not-found\n    user=> (get #{\"a\" 'b 5 nil} nil :not-found)\n    nil\n\nSimilarly for maps:\n\n    user=> (get {\"a\" 1, \"b\" nil} \"b\")\n    nil            ; found key \"b\", but value is nil\n    user=> (get {\"a\" 1, \"b\" nil} \"b\" :not-found)\n    nil            ; here we can tell it was found and value is nil\n    user=> (get {\"a\" 1, \"b\" nil} \"c\" :not-found)\n    :not-found     ; but here no key \"c\" was found\n\nIf you want a simpler way to determine whether a key is in a map or an\nelement is in a set, without having to know a not-found value that is\nguaranteed not to be a return value for a key/element in the\ncollection, use contains?.\n\nThe only conditions in which 'get' will throw an exception is\nindirectly, e.g. because you called it on a sorted set or map, and the\ncomparator function throws an exception when comparing two values.  It\nwill not even throw an exception if you use an out-of-bounds index for\na vector or array:\n\n    user=> (get [:a :b :c] 50)\n    nil\n\nClojure allows you to leave out the 'get' to achieve a more concise\nsyntax for vectors and maps.  Differences:\n\n* No not-found value vectors.  You may use one for maps.\n* (my-vector idx) will throw an exception if idx is not an integer, or\n  out of the bounds for the vector.\n* It does not work for records, strings, or Java Maps or arrays.\n\n    user=> (def vec1 [:a :b :c])\n    #'user/vec1\n    user=> (vec1 2)\n    :c\n    user=> (vec1 3)\n    IndexOutOfBoundsException   clojure.lang.PersistentVector.arrayFor (PersistentVector.java:107)\n    user=> (vec1 3 :not-found)\n    ArityException Wrong number of args (2) passed to: PersistentVector  clojure.lang.AFn.throwArity (AFn.java:437)\n\n    user=> (def map1 {:a 1 :b 2})\n    #'user/map1\n    user=> (map1 :a)\n    1\n    user=> (map1 :c)\n    nil\n    user=> (map1 :c :not-found)\n    :not-found\n\nSimilar to contains?, 'get' has some unusual cases where non-integer\nnumbers will be rounded off to integers without any errors.\n\n    user=> (get [:a :b :c] 1.7)\n    nil     ; on a vector, not found\n    user=> (get (int-array [5 6 7]) -0.99)\n    5       ; on a Java array, truncate to int, then index is found\n\nAlso similar to contains?, 'get' indices are truncated to 32-bit ints,\nso some large integers that are out of index bounds for a vector,\nstring, or array can be truncated to 32-bit ints that are in range\nafter removing their most significant bits.\n\n    user=> (def long-truncates-to-int-0 (bit-shift-left 1 33))\n    user=> (get \"abc\" long-truncates-to-int-0)\n    \\a\n    user=> (get [:a :b :c] long-truncates-to-int-0)\n    :a\n\nSee also: contains?, get-in, find\n"}
  {:symbol "hash",
   :ns "clojure.core",
   :extended-docstring
   "hash returns a 32-bit integer hash value for any object.  It is\nsimilar to Java's hashCode, but it is consistent with Clojure = (with\na few exceptions -- see below).  hashCode is consistent with Java's\nequals method.\n\nWhen we say a hash function is consistent with =, it means that for\nany two values x1 and x2 where (= x1 x2) is true, (= (hash x1)\n(hash x2)) is also true.  This is an important property that allows\nhash to be used in the implementation of the hash-set and hash-map\ndata structures.\n\nhash is consistent with =, except for some BigIntegers, Floats, and\nDoubles.  This leads to incorrect behavior if you use them as set\nelements or map keys (see example below).  Convert BigIntegers to\nBigInt using (bigint x), and floats and doubles to a common type\nwith (float x) or (double x), to avoid this issue.  This behavior is\nby choice: http://dev.clojure.org/jira/browse/CLJ-1036\n\nExamples:\n\n    user=> (def x 8589934588)\n    #'user/x\n    user=> (= (bigint x) (biginteger x))\n    true\n    user=> (= (hash (bigint x)) (hash (biginteger x)))\n    false         ; hash is not consistent with = for all BigInteger values\n    user=> (def s1 (hash-set (bigint x)))\n    #'user/s1\n    user=> (def s2 (hash-set (biginteger x)))\n    #'user/s2\n    user=> s1\n    #{8589934588N}\n    user=> s2\n    #{8589934588}     ; s1 and s2 look the same\n    user=> (= (first s1) (first s2))\n    true              ; their elements are =\n    ;; However, the sets are not = because of hash inconsistency.\n    user=> (= s1 s2)\n    false\n\n    user=> (= (float 1.0e9) (double 1.0e9))\n    true\n    user=> (= (hash (float 1.0e9)) (hash (double 1.0e9)))\n    false       ; hash is not consistent with = for all float/double values\n\nSee also: (topic Equality)  (TBD)\n"}
  {:symbol "range",
   :ns "clojure.core",
   :extended-docstring
   "Examples:\n\n    user=> (range 11)\n    (0 1 2 3 4 5 6 7 8 9 10)\n    user=> (range 5 11)\n    (5 6 7 8 9 10)\n    user=> (range 5 11 2)\n    (5 7 9)\n\n    ;; Just as when increasing, when decreasing the final value is not\n    ;; included in the result.\n    user=> (range 11 0 -1)\n    (11 10 9 8 7 6 5 4 3 2 1)\n    user=> (range 11 -1 -1)\n    (11 10 9 8 7 6 5 4 3 2 1 0)\n\nBe cautious when using float or double values, due to round-off\nerrors.  This is especially true for range, because these round-off\nerrors can accumulate and increase over a large number of values.\n\n    user=> (count (range 0 100 1))\n    100\n    user=> (last (range 0 100 1))\n    99\n    user=> (count (range 0.0 10.0 0.1))\n    101\n    user=> (last (range 0.0 10.0 0.1))\n    9.99999999999998\n\nFunctions like double-range and rangef in namespace thalia.utils may\nbe closer to what you want in some cases.\n"}
  {:symbol "re-find",
   :ns "clojure.core",
   :extended-docstring
   "(re-find regex str) is a pure function that returns the results of\nthe first match only.  See re-seq if you want a sequence of all\nmatches.  (re-find matcher) mutates the matcher object.\n\nIf there are no parenthesized 'groups' in the regex, re-find either\nreturns the substring of s that matches, or nil if there is no match.\nIt also behaves this way if all parenthesized groups do not 'capture',\nbecause they begin with ?:\n\n    user=> (re-find #\"\\d+\" \"abc123def\")\n    \"123\"\n    user=> (re-find #\"\\d+\" \"abcdef\")\n    nil\n    user=> (re-find #\"(?:\\d+)\" \"abc123def\")\n    \"123\"\n\nIf there are capturing groups, then on a match re-find returns a\nvector where the first element is the string that matches the entire\nregex, and successive vector elements are either strings matching a\ncapture group, or nil if nothing matched that capture group.  Groups\nare ordered in the same way that their left parentheses occur in the\nstring.\n\n    user=> (def line \" RX packets:1871 errors:5 dropped:48 overruns:9\")\n    #'user/line\n\n    user=> (re-find #\"(\\S+):(\\d+)\" line)\n    [\"packets:1871\" \"packets\" \"1871\"]\n\n    ;; groups can nest\n    user=> (re-find #\"(\\S+:(\\d+)) \\S+:\\d+\" line)\n    [\"packets:1871 errors:5\" \"packets:1871\" \"1871\"]\n\n    ;; If there is no match, re-find always returns nil, whether there\n    ;; are parenthesized groups or not.\n    user=> (re-find #\"(\\S+):(\\d+)\"\n                    \":2 numbers but not 1 word-and-colon: before\")\n    nil\n\n    ;; A capture group can have nil as its result if it is part of an\n    ;; 'or' (separated by | in the regex), and another alternative is\n    ;; the one that matches.\n\n    user=> (re-find #\"(\\D+)|(\\d+)\" \"word then number 57\")\n    [\"word then number \" \"word then number \" nil]\n\n    user=> (re-find #\"(\\D+)|(\\d+)\" \"57 number then word\")\n    [\"57\" nil \"57\"]\n\n    ;; It is also possible for a group to match the empty string.\n    user=> (re-find #\"(\\d*)(\\S)\\S+\" \"lots o' digits 123456789\")\n    [\"lots\" \"\" \"l\"]\n\nSee also: re-seq, re-matches, re-pattern, clojure.string/replace,\nclojure.string/replace-first, re-matcher, re-groups\n\nSee docs for function subs, section 'Memory use warning'.\n"}
  {:symbol "re-matches",
   :ns "clojure.core",
   :extended-docstring
   "(re-matches regex s) is the same as (re-find regex s), except that\nre-matches only returns a match result if the regex can be matched\nagainst the entire string.  re-find returns a match if the regex can\nbe matched against any substring of the given string.\n\n    user=> (re-find #\"\\d+\" \"abc123def\")\n    \"123\"\n    user=> (re-matches #\"\\d+\" \"abc123def\")\n    nil\n    user=> (re-matches #\"\\d+\" \"123\")\n    \"123\"\n\nSee the extended docs of re-find for additional examples, and notes on\nhow the return value is a vector when there are capture groups in the\nregex.\n\nSee also: re-find, re-seq, re-pattern, clojure.string/replace,\nclojure.string/replace-first, re-matcher, re-groups\n\nSee docs for function subs, section 'Memory use warning'.\n"}
  {:symbol "re-seq",
   :ns "clojure.core",
   :extended-docstring
   "(re-seq regex s) is the same as (re-find regex s), except that re-seq\nreturns a sequence of all matches, not only the first match.  It\nreturns nil if there were no matches.  Capture groups are handled the\nsame way as for re-find.\n\n    user=> (re-seq #\"\\d\" \"Mac OS X 10.6.8\")\n    (\"1\" \"0\" \"6\" \"8\")\n    user=> (re-seq #\"\\d+\" \"Mac OS X 10.6.8\")\n    (\"10\" \"6\" \"8\")\n    user=> (re-seq #\"ZZ\" \"Mac OS X 10.6.8\")\n    nil\n\n    ;; Capture groups in the regex cause each returned match to be a\n    ;; vector of matches.  See re-find for more examples.\n    user=> (re-seq #\"\\S+:\\d+\" \" RX pkts:18 err:5 drop:48\")\n    (\"pkts:18\" \"err:5\" \"drop:48\")\n    user=> (re-seq #\"(\\S+):(\\d+)\" \" RX pkts:18 err:5 drop:48\")\n    ([\"pkts:18\" \"pkts\" \"18\"] [\"err:5\" \"err\" \"5\"] [\"drop:48\" \"drop\" \"48\"])\n\nSee also: re-find, re-matches, re-pattern, clojure.string/replace,\nclojure.string/replace-first, re-matcher, re-groups\n\nSee docs for function subs, section 'Memory use warning'.\n"}
  {:symbol "read-string",
   :ns "clojure.core",
   :extended-docstring
   "WARNING: You *SHOULD NOT* use clojure.core/read-string to read data\nfrom untrusted sources.  See clojure.core/read docs.  The same\nsecurity issues exist for both read and read-string.\n"}
  {:symbol "read",
   :ns "clojure.core",
   :extended-docstring
   "You *SHOULD NOT* use clojure.core/read or clojure.core/read-string\nto read data from untrusted sources.  They were designed only for\nreading Clojure code and data from trusted sources (e.g. files that\nyou know you wrote yourself, and no one else has permission to modify\nthem).\n\nInstead, either:\n\n1. Use another data serialization format such as JSON, XML, etc. and a\n   library for reading them that you trust not to have\n   vulnerabilities, or\n\n2. if you want a serialization format that can be read safely and\n   looks like Clojure data structures, use edn.  For Clojure 1.3 and\n   later, the tools.reader contrib library provides an edn reader.\n   There is also clojure.edn/read and clojure.edn/read-string provided\n   in Clojure 1.5.\n\n[edn]: https://github.com/edn-format/edn\n[tools.reader]: http://github.com/clojure/tools.reader\n\nYou definitely should not use clojure.core/read or read-string if\n*read-eval* has its default value of true, because an attacker\ncould cause your application to execute arbitrary code while it is\nreading.  Example:\n\n    user> (read-string \"#=(clojure.java.shell/sh \\\"echo\\\" \\\"hi\\\")\")\n    {:exit 0, :out \"hi\\n\", :err \"\"}\n\nIt is straightforward to modify the example above into more\ndestructive ones that remove all of your files, copy them to someone\nelse's computer over the Internet, install Trojans, etc.\n\nEven if you bind *read-eval* to false first, like so:\n\n    (defn read-string-unsafely [s]\n      (binding [*read-eval* false]\n        (read-string s)))\n\nyou may hope you are safe reading untrusted data that way, but in\nClojure 1.4 and earlier, an attacker can send data that causes your\nsystem to execute arbitrary Java constructors.  Most of these are\nbenign, but it only takes one to ruin your application's day.\nExamples that should scare you:\n\n    ;; This causes a socket to be opened, as long as the JVM\n    ;; sandboxing allows it.\n    (read-string-unsafely \"#java.net.Socket[\\\"www.google.com\\\" 80]\")\n\n    ;; This causes precious-file.txt to be created if it doesn't\n    ;; exist, or if it does exist, its contents will be erased (given\n    ;; appropriate JVM sandboxing permissions, and underlying OS file\n    ;; permissions).\n    (read-string-unsafely \"#java.io.FileWriter[\\\"precious-file.txt\\\"]\")\n\nThe particular issue of executing arbitrary Java constructors used in\nthe examples above no longer works in Clojure 1.5 when *read-eval*\nis false.  Even so, you *SHOULD NEVER USE* clojure.core/read or\nclojure.core/read-string for reading untrusted data.  Use an edn\nreader or a different data serialization format.\n\nWhy should I do this, you may ask, if Clojure 1.5 closes the Java\nconstructor hole?  Because clojure.core/read and read-string are\ndesigned to be able to do dangerous things, and they are not\ndocumented nor promised to be safe from unwanted side effects.  If you\nuse them for reading untrusted data, and a dangerous side effect is\nfound in the future, you will be told that you are using the wrong\ntool for the job.  clojure.edn/read and read-string, and the\ntools.reader.edn library, are documented to be safe from unwanted\nside effects, and if any bug is found in this area it should get quick\nattention and corrected.\n\nIf you understand all of the above, and want to use read or\nread-string to read data from a _trusted_ source, continue on below.\n\n    ;; read wants its reader arg (or *in*) to be a\n    ;; java.io.PushbackReader.  with-open closes r after the with-open\n    ;; body is done.  *read-eval* specifies whether to allow #=()\n    ;; forms when reading, and evaluate them as a side effect while\n    ;; reading.\n\n    (defn read-from-file-with-trusted-contents [filename]\n      (with-open [r (java.io.PushbackReader.\n                      (clojure.java.io/reader filename))]\n        (binding [*read-eval* false]\n          (read r))))\n\n    user> (spit \"testfile.txt\" \"{:a 1 :b 2 :c 3}\")\n    nil\n    user> (read-from-file-with-trusted-contents \"testfile.txt\")\n    {:a 1, :b 2, :c 3}\n"}
  {:symbol "sort-by",
   :ns "clojure.core",
   :extended-docstring
   "See extended docs for sort, all of which applies to sort-by.\n\nExamples:\n\n    user=> (sort-by count [\"lummox\" \"antidisestablishmentarianism\" \"a\"])\n    (\"a\" \"lummox\" \"antidisestablishmentarianism\")\n    user=> (sort-by first > [[8.67 -5] [5 1] [-22/7 3.0] [5 0]])\n    ([8.67 -5] [5 1] [5 0] [-22/7 3.0])\n\nThe example in sort extended docs demonstrating a Java array being\nmodified applies to sort-by, too, including using aclone to copy the\narray before sorting to avoid that issue.\n\nSee also: sort, compare, (topic Comparators)\n"}
  {:symbol "sort",
   :ns "clojure.core",
   :extended-docstring
   "If you supply a comparator, it must implement the Java Comparator\ninterface, but this includes Clojure functions that implement a 3-way\nor boolean comparator.  See (topic Comparators) for details on boolean\ncomparators.\n\nsort is guaranteed to be stable, since it is implemented using the\nsort method of Java's java.util.Arrays class.  This means that if two\nvalues in the input collection are considered equal by the comparator,\nthey are guaranteed to remain in the same relative order in the output\nas they had in the input.\n\nExamples:\n\n    user=> (sort [3 -7 10 8 5.3 9/5 -7.1])\n    (-7.1 -7 9/5 3 5.3 8 10)\n    user=> (sort #(compare %2 %1) '(apple banana aardvark zebra camel))\n    (zebra camel banana apple aardvark)\n\n    user=> (def x (to-array [32 9 11]))\n    #'user/x\n    user=> (seq x)\n    (32 9 11)\n    user=> (sort x)   ; returns sorted sequence\n    (9 11 32)\n    user=> (seq x)    ; but also modifies Java array x\n    (9 11 32)\n    user=> (sort (aclone x))   ; can avoid this by copying the array\n    (9 11 32)\n    ;; Such copying is unnecessary for args that are not a Java array\n\nSee also: sort-by, compare, (topic Comparators)\n"}
  {:symbol "sorted-map-by",
   :ns "clojure.core",
   :extended-docstring
   "sorted-map-by returns a sorted map that maintains its keys in sorted\norder, as determined by the given comparator function.  See sorted-map\ndocs for the differences between sorted and unsorted maps.\n\nBe cautious when writing your own comparators, especially for sorted\nmaps.  Remember that all maps follow the rule of 'first equal key to\nbe added wins'.  If your comparator function compares two values as\nequal, then at most one of them can be a key in a sorted map at one\ntime.  See the 'Sorted sets and maps' section of (topic\nComparators) (TBD) for more discussion.\n\nExamples:\n\n    user=> (sorted-map-by > 2 \"two\" 3 \"three\" 11 \"eleven\" 5 \"five\" 7 \"seven\")\n    {11 \"eleven\", 7 \"seven\", 5 \"five\", 3 \"three\", 2 \"two\"}\n    user=> (sorted-map-by #(compare %2 %1)\n                          \"aardvark\" \"Orycteropus afer\"\n                          \"lion\" \"Panthera leo\"\n                          \"platypus\" \"Ornithorhynchus anatinus\")\n    {\"platypus\" \"Ornithorhynchus anatinus\",\n     \"lion\" \"Panthera leo\",\n     \"aardvark\" \"Orycteropus afer\"}\n\nWith comparator case-insensitive-cmp below, \"Lion\" is equal to \"lion\"\nand not added as a separate key in the map.  The value associated with\nthe second equal key \"Lion\" does replace the first value.\n\n    user=> (require '[clojure.string :as str])\n    nil\n    user=> (defn case-insensitive-cmp [s1 s2]\n             (compare (str/lower-case s1) (str/lower-case s2)))\n    #'user/case-insensitive-cmp\n    user=> (sorted-map-by case-insensitive-cmp \"lion\" \"normal lion\"\n                                               \"Lion\" \"Orycteropus afer\")\n    {\"lion\" \"Orycteropus afer\"}\n\nSee also: sorted-map, (topic Comparators)\n"}
  {:symbol "sorted-map",
   :ns "clojure.core",
   :extended-docstring
   "Sorted maps maintain their keys in sorted order, sorted by the\nfunction compare.  Use sorted-map-by to get a different key order.\n\n    ;; function compare sorts keywords alphabetically\n    user=> (sorted-map :d 0 :b -5 :a 1)\n    {:a 1, :b -5, :d 0}\n    user=> (assoc (sorted-map :d 0 :b -5 :a 1) :c 57)\n    {:a 1, :b -5, :c 57, :d 0}\n\nSorted maps are in most ways similar to unsorted maps.  Differences\ninclude:\n\n* seq returns a sequence of the key/value pairs in order, sorted by\n  their keys.  This affects all other sequence-based operations upon\n  sorted maps, e.g. first, rest, map, for, doseq, and many others.\n* rseq returns this same sequence but in reverse order.  It does so\n  lazily, unlike (reverse (seq coll)), which must generate the entire\n  sequence before it can reverse it.\n* You can use subseq or rsubseq on a sorted map to get a sorted\n  sequence of all key/value pairs with keys in a specified range.\n* Unsorted maps use = to compare keys, but sorted maps use compare or\n  a caller-supplied comparator.  A sorted map's comparator can throw\n  exceptions if you put incomparable keys in the same map.\n* There is no transient version of sorted maps.\n\nExamples:\n\n    user=> (def births\n             (sorted-map -428 \"Plato\"      -384 \"Aristotle\" -469 \"Socrates\"\n                         -320 \"Euclid\"     -310 \"Aristarchus\" 90 \"Ptolemy\"\n                         -570 \"Pythagoras\" -624 \"Thales\"    -410 \"Eudoxus\"))\n    #'user/births\n    user=> (first births)\n    [-624 \"Thales\"]\n    user=> (take 4 births)\n    ([-624 \"Thales\"] [-570 \"Pythagoras\"] [-469 \"Socrates\"] [-428 \"Plato\"])\n    user=> (keys births)\n    (-624 -570 -469 -428 -410 -384 -320 -310 90)\n    user=> (vals births)   ; returns values in order by sorted keys\n    (\"Thales\" \"Pythagoras\" \"Socrates\" \"Plato\" \"Eudoxus\" \"Aristotle\" \"Euclid\" \"Aristarchus\" \"Ptolemy\")\n\nsubseq and rsubseq return a sequence of all key/value pairs with a\nspecified range of keys.  It takes O(log N) to find the first pair,\nwhere N is the size of the whole map, and O(1) time for each\nadditional pair, so it is more efficient than the O(N) approach of\ntaking the entire sequence and filtering out the unwanted pairs.\n\n    user=> (subseq births > -400)\n    ([-384 \"Aristotle\"] [-320 \"Euclid\"] [-310 \"Aristarchus\"] [90 \"Ptolemy\"])\n    user=> (subseq births > -400 < -100)\n    ([-384 \"Aristotle\"] [-320 \"Euclid\"] [-310 \"Aristarchus\"])\n    user=> (rsubseq births > -400 < -100)\n    ([-310 \"Aristarchus\"] [-320 \"Euclid\"] [-384 \"Aristotle\"])\n\nBoth unsorted and sorted maps follow the rule of 'first equal key to\nbe added wins'.  The difference is in what keys they consider to be\nequal: unsorted uses =, sorted uses compare or a custom comparator.\n\n    user=> (def m1 (hash-map 1.0 \"floatone\" 1 \"intone\" 1.0M \"bigdecone\"\n                             1.5M \"bigdec1.5\" 3/2 \"ratio1.5\"))\n    #'user/m1\n    user=> m1     ; every key is unique according to =\n    {1.0 \"floatone\", 1 \"intone\", 3/2 \"ratio1.5\", 1.5M \"bigdec1.5\",\n     1.0M \"bigdecone\"}\n    user=> (dissoc m1 1 3/2)\n    {1.0 \"floatone\", 1.5M \"bigdec1.5\", 1.0M \"bigdecone\"}\n\n    ;; compare treats 1.0, 1, 1.0M as equal, so first of those keys\n    ;; wins.  Similarly for 1.5M and 3/2.  Note that the last *value*\n    ;; for any equal key wins, as you should expect when assoc'ing\n    ;; key/vals to a map.\n    user=> (def m2 (sorted-map 1.0 \"floatone\" 1 \"intone\" 1.0M \"bigdecone\"\n                               1.5M \"bigdec1.5\" 3/2 \"ratio1.5\"))\n    #'user/m2\n    user=> m2\n    {1.0 \"bigdecone\", 1.5M \"ratio1.5\"}\n    user=> (dissoc m2 1 3/2)\n    {}       ; removing a key only needs equality according to compare\n\nYou may search an unsorted map for any value with no exception.\n\n    user=> (m1 1)\n    \"intone\"\n    user=> (m1 \"a\")\n    nil     ; no exception, just nil indicating no such key \"a\"\n\nSearching sorted maps calls the comparator with the searched-for value\nand some of the keys in the map, which throws an exception if the\ncomparator does.\n\n    user=> (m2 1)\n    \"bigdecone\"\n    user=> (m2 \"a\")   ; this gives exception from compare\n    ClassCastException java.lang.Double cannot be cast to java.lang.String  java.lang.String.compareTo (String.java:108)\n\nSorted maps maintain the key/value pairs in sorted order by key using\na persistent red-black tree data structure.  It takes O(log N) time to\nadd or remove a key/value pair, but the constant factors involved are\ntypically larger than for unsorted maps.\n\nSee also: sorted-map-by, compare, hash-map, assoc, dissoc, keys, vals,\n          subseq, rsubseq\n"}
  {:symbol "sorted-set-by",
   :ns "clojure.core",
   :extended-docstring
   "sorted-set-by returns a sorted set that maintains its elements in\nsorted order, as determined by the given comparator function.  See\nsorted-set docs for the differences between sorted and unsorted sets.\n\nBe cautious when writing your own comparators, especially for sorted\nsets.  Remember that all sets follow the rule of 'first equal element\nto be added wins'.  If your comparator function compares two values as\nequal, then at most one of them can be an element in a sorted set at\none time.  See the 'Sorted sets and maps' section of (topic\nComparators) (TBD) for more discussion.\n\nExamples:\n\n    user=> (sorted-set-by compare \"Food\" \"good\" \"air\" \"My\" \"AiR\" \"My\")\n    #{\"AiR\" \"Food\" \"My\" \"air\" \"good\"}\n\nWith case-insensitive-cmp, \"AiR\" is a duplicate with \"air\" and\nnot added to the set, and the order is different.\n\n    user=> (require '[clojure.string :as str])\n    nil\n    user=> (defn case-insensitive-cmp [s1 s2]\n             (compare (str/lower-case s1) (str/lower-case s2)))\n    #'user/case-insensitive-cmp\n    user=> (sorted-set-by case-insensitive-cmp\n                          \"Food\" \"good\" \"air\" \"My\" \"AiR\" \"My\")\n    #{\"air\" \"Food\" \"good\" \"My\"}\n\nSee also: sorted-set, (topic Comparators)\n"}
  {:symbol "sorted-set",
   :ns "clojure.core",
   :extended-docstring
   "Sorted sets maintain their elements in sorted order, sorted by the\nfunction compare.  Use sorted-set-by to get a different element order.\n\nSorted sets are in most ways similar to unsorted sets.  Read the docs\nfor sorted-map to learn how sorted _maps_ differ from unsorted maps.\nAll of those differences apply equally to how sorted sets differ from\nunsorted sets, if you replace 'key/value pairs' with 'elements', and\nsorting by keys with sorting by elements.\n\n    user=> (sorted-set 4 2 1)\n    #{1 2 4}\n    user=> (conj (sorted-set 4 2 1) 3)\n    #{1 2 3 4}\n\n    user=> (range 100 0 -5)\n    (100 95 90 85 80 75 70 65 60 55 50 45 40 35 30 25 20 15 10 5)\n    user=> (def ss (apply sorted-set (range 100 0 -5)))\n    #'user/ss\n    user=> ss\n    #{5 10 15 20 25 30 35 40 45 50 55 60 65 70 75 80 85 90 95 100}\n    user=> (first ss)\n    5\n    user=> (last ss)\n    100\n    user=> (subseq ss >= 80)\n    (80 85 90 95 100)\n    user=> (subseq ss > 20 < 60)\n    (25 30 35 40 45 50 55)\n\nSee also: sorted-set-by, sorted-map, compare, hash-set, conj, disj,\n          subseq, rsubseq\n"}
  {:symbol "subs",
   :ns "clojure.core",
   :extended-docstring
   "The index of the first character is 0.  An exception will be\nthrown if you use negative values -- it will not index characters from\nthe end of the string like similar functions in some other programming\nlanguages.  If you use non-integer values for start or end, they will\nbe auto-converted to integers as if by (int x).\n\nExamples:\n\n    user=> (subs \"abcdef\" 1 3)\n    \"bc\"\n    user=> (subs \"abcdef\" 1)\n    \"bcdef\"\n    user=> (subs \"abcdef\" 4 6)\n    \"ef\"\n    user=> (subs \"abcdef\" 4 7)\n    StringIndexOutOfBoundsException String index out of range: 7  java.lang.String.substring (String.java:1907)\n    user=> (subs \"abcdef\" 5/3 6.28)   ; args converted to ints 1 6\n    \"bcdef\"\n\nMemory use warning:\n\nsubs, and many other functions that return substrings of a larger\none (e.g. re-find, re-seq, etc.) are based on Java's substring method\nin class String.  Before Java version 7u6, this was implemented in\nO(1) time by creating a String object that referred to an offset and\nlength within the original String object, thus retaining a reference\nto the original as long as the substrings were referenced.  This can\ncause unintentionally large memory use if you create large strings,\nand then create small substrings of them with subs and similar\nfunctions.  The large strings cannot be garbage collected because of\nthe references to them from the substrings.\n\nIn Java version 7u6, Java's substring() method behavior changed to\ncopy the desired substring into a new String object, so no references\nare kept to the original.\n\n    http://www.javaadvent.com/2012/12/changes-to-stringsubstring-in-java-7.html\n\nIf you wish to force the copying behavior, you can use the String\nconstructor (String. s).\n"})}
