=======
libfsmg
=======

Synopsis
--------

A generic finite state machine library.

Overview
--------

This library provides two means of pattern matching: Knuth-Morris-Pratt (KMP;
for the Iterator interface) and Boyer-Moore (BM; for the List interface)
algorithm-based **exact matchers** (i.e., two DFAs) and a weighted
backtracking-based **pattern matcher** using dynamic programming that provides
Kleene closures (``*``, ``+``, and ``?``) and capture groups (``(`` ... ``)``)
for the matched patterns (i.e., a NFA). The entire library is implemented
using *generics* (i.e., requires Java 1.5 and beyond), so that any sequence of
types may be used (arrays, Lists and any sequence that provides the Iterator
protocol). The APIs are implemented as close as possible to Java's own Pattern
and Matcher APIs in ``util.regex``.

Usage
-----

See the
`Java API <http://htmlpreview.github.com?http://github.com/fnl/libfsmg/blob/master/doc/index.html>`_
index page of this library for general information,
or the specific notes on using an
`ExactMatcher <http://htmlpreview.github.com?http://github.com/fnl/libfsmg/blob/master/doc/es/fnl/fsm/ExactMatcher.html>`_
(a BM List matcher), the
`ExactScanner <http://htmlpreview.github.com?http://github.com/fnl/libfsmg/blob/master/doc/es/fnl/fsm/ExactScanner.html>`_
 (a KMP Iterator scanner) and on implementing the NFA
`Pattern <http://htmlpreview.github.com?http://github.com/fnl/libfsmg/blob/master/doc/es/fnl/fsm/Pattern.html>`_
matcher. For the pattern matcher, refer to the ``txtfnnl``
`project <http://github.com/fnl/txtfnnl>`_
to find examples of the
`Transition interface <http://github.com/fnl/txtfnnl/blob/master/txtfnnl-uima/src/main/java/txtfnnl/uima/pattern/TokenTransition.java>`_
, the
`Pattern implementation <http://github.com/fnl/txtfnnl/blob/master/txtfnnl-uima/src/main/java/txtfnnl/uima/pattern/SyntaxPattern.java>`_
, and an actual
`CFG pattern compiler <https://github.com/fnl/txtfnnl/blob/master/txtfnnl-uima/src/main/java/txtfnnl/uima/pattern/RegExParser.java>`_
.

Installation
------------

Clone from github ( ``git clone git://github.com/fnl/libfsmg.git`` ),
and run ``mvn install`` in the newly created ``libfsmg`` directory.

License, Author and Copyright Notice
------------------------------------

**libfsmg** is free, open source software provided via a
`Apache 2.0 License <http://www.apache.org/licenses/LICENSE-2.0.html>`_ -
see ``LICENSE.txt`` in this directory for details.

Copyright 2012, 2013 - Florian Leitner (fnl). All rights reserved.