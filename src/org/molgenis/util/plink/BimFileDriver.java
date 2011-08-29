package org.molgenis.util.plink;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.molgenis.util.CsvFileReader;
import org.molgenis.util.CsvReaderListener;
import org.molgenis.util.Tuple;

/**
 * Driver to query BIM files. BIM files annotate the genotypes of BED files.
 * See: http://pngu.mgh.harvard.edu/~purcell/plink/binary.shtml
 * 
 * Content of a BIM file: chromosome, SNP, cM, base-position, allele 1, allele 2
 */
public class BimFileDriver
{

	private CsvFileReader reader;
	private long nrOfElements;

	/**
	 * Get the number of retrievable annotation elements of this BIM file.
	 * 
	 * @return
	 */
	public long getNrOfElements()
	{
		return nrOfElements;
	}

	/**
	 * Construct a BimFileDriver on this file
	 * 
	 * @param bimFile
	 * @throws Exception
	 */
	public BimFileDriver(File bimFile) throws Exception
	{
		reader = new CsvFileReader(bimFile);
		reader.disableHeader(false);

		if (reader.fileEndsWithNewlineChar())
		{
			this.nrOfElements = reader.getNumberOfLines() - 1;
		}
		else
		{
			this.nrOfElements = reader.getNumberOfLines();
		}

	}

	/**
	 * Close the underlying file reader
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException
	{
		this.reader.close();
	}

	/**
	 * Get a specific set of BIM file entries
	 * 
	 * @param from
	 *            = inclusive
	 * @param to
	 *            = exclusive
	 * @return
	 * @throws Exception
	 */
	public List<BimEntry> getEntries(final long from, final long to)
			throws Exception
	{
		reader.reset();
		final ArrayList<BimEntry> result = new ArrayList<BimEntry>();
		reader.parse(new CsvReaderListener()
		{
			public void handleLine(int line_number, Tuple tuple)
			{
				if (line_number - 1 >= from && line_number - 1 < to)
				{
					BimEntry be = new BimEntry(tuple.getString(0), tuple
							.getString(1), tuple.getDouble(2),
							tuple.getLong(3), new Character(tuple.getString(4)
									.charAt(0)), new Character(tuple.getString(
									5).charAt(0)));
					result.add(be);
				}
			}
		});
		return result;
	}

	/**
	 * Get all BIM file entries
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<BimEntry> getAllEntries() throws Exception
	{
		reader.reset();
		final ArrayList<BimEntry> result = new ArrayList<BimEntry>();
		reader.parse(new CsvReaderListener()
		{
			public void handleLine(int line_number, Tuple tuple)
			{
				BimEntry be = new BimEntry(tuple.getString(0), tuple
						.getString(1), tuple.getDouble(2), tuple.getLong(3),
						new Character(tuple.getString(4).charAt(0)),
						new Character(tuple.getString(5).charAt(0)));
				result.add(be);
			}
		});
		return result;
	}

}