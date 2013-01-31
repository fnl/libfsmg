libfsmg
=======

A generic finite state machine library.

This library provides two means of pattern matching: a Knuth-Morris-Pratt
algorithm-based exact matcher (i.e., a deterministic FSM) and a weighted
backtracking-based matcher using dynamic programming that provides the Kleene
closure and capture groups for the matched patterns. The entire library is
implemented using generics (i.e., requires Java 1.5 and beyond), so that any
sequence of types may be used (arrays and any sequence that provides the
Iterator protocol).

See the
`Java API <http://htmlpreview.github.com?http://github.com/fnl/libfsmg/blob/master/doc/index.html>`_
for general information on using this library,
including general notes on implementing an
`exact matcher <http://htmlpreview.github.com?http://github.com/fnl/libfsmg/blob/master/doc/es/fnl/fsm/ExactMatcher.html>`_
and on implementing the
`pattern matcher <http://htmlpreview.github.com?http://github.com/fnl/libfsmg/blob/master/doc/es/fnl/fsm/Pattern.html>`_
. For the exact matcher, have a look at the
`test case source <http://github.com/fnl/libfsmg/blob/master/src/test/java/es/fnl/fsm/TestExactMatcher.java>`_
to understand how to implement it. For the pattern matching,
refer to the ``txtfnnl`` Maven project of the
`Transition interface <http://github.com/fnl/txtfnnl/blob/master/txtfnnl-uima/src/main/java/txtfnnl/uima/pattern/TokenTransition.java>`_
, the
`Pattern implementation <http://github.com/fnl/txtfnnl/blob/master/txtfnnl-uima/src/main/java/txtfnnl/uima/pattern/SyntaxPattern.java>`_
, and the actual
`CFG pattern compiler <https://github.com/fnl/txtfnnl/blob/master/txtfnnl-uima/src/main/java/txtfnnl/uima/pattern/RegExParser.java>`_
.

Installation
------------

Clone from github (``git clone git://github.com/fnl/libfsmg.git``),
and run ``mvn install`` in the newly created ``libfsmg`` directory.

License, Author and Copyright Notice
------------------------------------

**libfsmg** is free, open software provided via a
`Apache 2.0 License <http://www.apache.org/licenses/LICENSE-2.0.html>`_ -
see ``LICENSE.txt`` in this directory for details.

Copyright 2012, 2013 - Florian Leitner (fnl). All rights reserved.