package com.dzeru.combalph.objects;

/**
   Copyright 2018, Dzeru, Combalph

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

public enum ComplexityLevel
{
	EXTREMELY_EASY(20), EASY(15), NORMAL(10), HARD(5), EXTREMELY_HARD(1);

	private int complvl;

	ComplexityLevel(int complvl)
	{
		this.complvl = complvl;
	}

	public int getComplvl()
	{
		return complvl;
	}
}