package com.dzeru.combalph.objects;

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