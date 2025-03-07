//  GenericBinarySolutionTest.java
//
//  Author:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//
//  Copyright (c) 2014 Antonio J. Nebro
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
//
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

package org.uma.jmetal.solution.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.uma.jmetal.problem.BinaryProblem;
import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.BinarySolution;

import static org.junit.Assert.assertEquals;

public class GenericBinarySolutionTest {
  private static final int NUMBER_OF_BITS_OF_MOCKED_BINARY_PROBLEM = 5 ;
  BinaryProblem problem ;

  @Before public void setUp(){
    problem = new MockBinaryProblem();
  }

  @After public void tearDown(){
    problem = null ;
  }

  @Test public void shouldTheSumOfGetNumberOfBitsBeEqualToTheSumOfBitsPerVariable() {
    GenericBinarySolution solution = (GenericBinarySolution) problem.createSolution();

    assertEquals(problem.getTotalNumberOfBits(), solution.getTotalNumberOfBits());
  }

  @Test public void shouldGetNumberOfBitsBeEqualToTheNumberOfOfBitsPerVariable() {
    GenericBinarySolution solution = (GenericBinarySolution) problem.createSolution();

    for (int i = 0; i < problem.getNumberOfVariables(); i++) {
      assertEquals(problem.getNumberOfBits(i), solution.getNumberOfBits(i));
    }
  }

  @Test public void shouldCopyReturnAnIdenticalVariable() {
    GenericBinarySolution expectedSolution = (GenericBinarySolution) problem.createSolution();
    GenericBinarySolution newSolution = (GenericBinarySolution) expectedSolution.copy();
    assertEquals(expectedSolution, newSolution);
  }

  @Test public void shouldGetTotalNumberOfBitsBeEqualToTheSumOfBitsPerVariable() {
    assertEquals(NUMBER_OF_BITS_OF_MOCKED_BINARY_PROBLEM*problem.getNumberOfVariables(),
        problem.getTotalNumberOfBits());
  }

  @Test public void shouldGetVariableValueStringReturnARightStringRepresentation() throws Exception {
    GenericBinarySolution solution = (GenericBinarySolution) problem.createSolution();
    solution.getVariableValue(0).set(0, NUMBER_OF_BITS_OF_MOCKED_BINARY_PROBLEM) ;

    assertEquals("11111", solution.getVariableValueString(0)) ;

    solution.getVariableValue(0).clear(2) ;
    assertEquals("11011", solution.getVariableValueString(0)) ;
  }

  /**
   * Mock class representing a binary problem
   */
  private class MockBinaryProblem extends AbstractBinaryProblem {
    private int[] bitsPerVariable ;
    /** Constructor */
    public MockBinaryProblem() {
      this(3);
    }

    /** Constructor */
    public MockBinaryProblem(Integer numberOfVariables) {
      setNumberOfVariables(numberOfVariables);
      setNumberOfObjectives(2);

      bitsPerVariable = new int[numberOfVariables] ;

      for (int var = 0; var < numberOfVariables; var++) {
        bitsPerVariable[var] = NUMBER_OF_BITS_OF_MOCKED_BINARY_PROBLEM;
      }
    }

    @Override
    protected int getBitsPerVariable(int index) {
      return bitsPerVariable[index] ;
    }

    @Override
    public BinarySolution createSolution() {
      return new GenericBinarySolution(this) ;
    }

    /** Evaluate() method */
    @Override
    public void evaluate(BinarySolution solution) {
      solution.setObjective(0, 0);
      solution.setObjective(1, 1);
    }
  }
}
