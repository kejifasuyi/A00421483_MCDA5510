//  File ContactGenerator
//  Sample code was taken from:
//  http://www.csharpprogramming.tips/2013/06/RandomDoxGenerator.html
//  Other useful methods are there.
//
// Requirements:
// Exapand on the below example to create a CSV file (https://en.wikipedia.org/wiki/Comma-separated_values)
// For contacts with the following data
// First Name
// Last Name
// Street Number
// Street
// City
// 
// Province
// Country  == Canada ( Simply insert "canada")
// Postal Code  
// 
// Generate 20 records in the file, submit end of class


using System;
using System.IO;

// Describes what is a namespace 
// https://docs.microsoft.com/en-us/dotnet/csharp/programming-guide/namespaces/
namespace MSCDA5510
{
    class ContactGenerator
    {
        // instance of random number generator
        Random rand = new Random();

        static void Main(string[] args)
        {
            // instance of ContactGenerator
            ContactGenerator dg =  new ContactGenerator();
        }

        public ContactGenerator()
        {
            String COMMA = ",";

            //TODO replace with your local location
            String outputFileName = @"C:\Users\Dunnyfashion\Documents\GitHub\A00421483_MCDA5510\ConsoleApp1\customers.csv";

            if (File.Exists(outputFileName))
            {
                Console.Write(" File " + outputFileName + " exists, appending");
            }
            StreamWriter fileStream = new StreamWriter(outputFileName, true);

            // Write Header
            fileStream.Write("First Name");
            fileStream.Write(COMMA);
            fileStream.Write("Last Name");
            fileStream.Write(COMMA);
            fileStream.Write("Street No.");
            fileStream.Write(COMMA);
            fileStream.Write("Street");
            fileStream.Write(COMMA);
            fileStream.Write("City");
            fileStream.Write(COMMA);
            fileStream.Write("Province");
            fileStream.Write(COMMA);
            fileStream.Write("Country");
            fileStream.WriteLine();

            for (int i = 0; i < 20; i++)
            {
                fileStream.Write(GenerateFirstName());
                fileStream.Write(COMMA);
                fileStream.Write(GenerateLastName());
                fileStream.Write(COMMA);
                fileStream.Write(GenerateStNo());
                fileStream.Write(COMMA);
                fileStream.Write(GenerateSt());
                fileStream.Write(COMMA);
                fileStream.Write(GenerateCity());
                fileStream.Write(COMMA);
                fileStream.Write(GeneratePr());
                fileStream.Write(COMMA);
                fileStream.Write("Canada");
                fileStream.WriteLine();
            }
            fileStream.Close();
        }

        private string GeneratePr()
        {
            String province = @"C:\Users\Dunnyfashion\Documents\GitHub\A00421483_MCDA5510\ConsoleApp1\ConsoleApp1\Province.txt";
            return ReturnRandomLine(province);
        }

        private string GenerateCity()
        {
            String city = @"C:\Users\Dunnyfashion\Documents\GitHub\A00421483_MCDA5510\ConsoleApp1\ConsoleApp1\City.txt";
            return ReturnRandomLine(city);
        }

        private string GenerateSt()
        {
            String street = @"C:\Users\Dunnyfashion\Documents\GitHub\A00421483_MCDA5510\ConsoleApp1\ConsoleApp1\Street.txt";
            return ReturnRandomLine(street);
        }

        private string GenerateStNo()
        {
            String stNo = @"C:\Users\Dunnyfashion\Documents\GitHub\A00421483_MCDA5510\ConsoleApp1\ConsoleApp1\StNo.txt";
            return ReturnRandomLine(stNo);
        }

        public string GenerateFirstName()
        {
            String firstNames = @"C:\Users\Dunnyfashion\Documents\GitHub\A00421483_MCDA5510\ConsoleApp1\ConsoleApp1\firstNames.txt";
            return ReturnRandomLine(firstNames);
        }

        public string GenerateLastName()
        {
            String lastNames = @"C:\Users\Dunnyfashion\Documents\GitHub\A00421483_MCDA5510\ConsoleApp1\ConsoleApp1\lastNames.txt";
            return ReturnRandomLine(lastNames);
        }

        // Gets a line from a file
        public string ReturnRandomLine(string FileName)
        {
            string sReturn = string.Empty;

            using (FileStream myFile = new FileStream(FileName, FileMode.Open, FileAccess.Read))
            {
                using (StreamReader myStream = new StreamReader(myFile))
                {

                    // just cast it to int because we know it will be less than 
                    int fileLength = (int)myFile.Length;

                    // Seek file stream pointer to a rand position...
                    myStream.BaseStream.Seek(rand.Next(1,fileLength), SeekOrigin.Begin);

                    // Read the rest of that line.
                    myStream.ReadLine();

                    // Return the next, full line...
                    sReturn = myStream.ReadLine();
                }
            }

            // If our random file position was too close to the end of the file, it will return an empty string
            // I avoided a while loop in the case that the file is empty or contains only one line
            if (System.String.IsNullOrWhiteSpace(sReturn))
            {
                sReturn = ReturnRandomLine(FileName);
            }

            return sReturn;
        }
    }




}
