	// Copyright 2011-2012 Paulo Augusto Peccin. See licence.txt distributed with this file.

package general.m6502.instructions;

import general.m6502.M6502;
import general.m6502.OperandType;
import general.m6502.UndocumentedInstruction;

public final class uDCP extends UndocumentedInstruction {

	public uDCP(M6502 cpu, int type) {
		super(cpu);
		this.type = type;
	}

	@Override
	public int fetch() {
		if (type == OperandType.Z_PAGE) 	{ ea = cpu.fetchZeroPageAddress(); return 5; }
		if (type == OperandType.Z_PAGE_X) 	{ ea = cpu.fetchZeroPageXAddress(); return 6; }
		if (type == OperandType.ABS) 		{ ea = cpu.fetchAbsoluteAddress(); return 6; }
		if (type == OperandType.ABS_X) 		{ ea = cpu.fetchAbsoluteXAddress(); return 7; }
		if (type == OperandType.ABS_Y) 		{ ea = cpu.fetchAbsoluteYAddress(); return 7; }
		if (type == OperandType.IND_X) 		{ ea = cpu.fetchIndirectXAddress(); return 8; }
		if (type == OperandType.IND_Y) 		{ ea = cpu.fetchIndirectYAddress(); return 8; }
		throw new IllegalStateException("uDCP Invalid Operand Type: " + type);
	}

	@Override
	public void execute() {
		final byte val = (byte) (cpu.memory.readByte(ea) - 1); 
		cpu.memory.writeByte(ea, val);
		int uVal = M6502.toUunsignedByte(val); 
		int uA = M6502.toUunsignedByte(cpu.A);
		cpu.CARRY = uA >= uVal;
		cpu.ZERO = uA == uVal;
		cpu.NEGATIVE = ((byte)(uA - uVal)) < 0;
	}

	private final int type;
	
	private int ea;
	

	public static final long serialVersionUID = 1L;

}
