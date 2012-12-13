package org.molgenis.io.processor;

public class TrimProcessor extends AbstractCellProcessor
{
	public TrimProcessor()
	{
		super();
	}

	public TrimProcessor(boolean processHeader, boolean processData)
	{
		super(processHeader, processData);
	}

	@Override
	public String process(String value)
	{
		return value != null ? value.trim() : null;
	}
}
